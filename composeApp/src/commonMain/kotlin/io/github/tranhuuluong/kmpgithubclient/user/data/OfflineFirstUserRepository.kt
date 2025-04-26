package io.github.tranhuuluong.kmpgithubclient.user.data

import io.github.tranhuuluong.kmpgithubclient.core.DataState
import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.core.Result
import io.github.tranhuuluong.kmpgithubclient.core.StateLoading
import io.github.tranhuuluong.kmpgithubclient.user.data.local.UserDao
import io.github.tranhuuluong.kmpgithubclient.user.data.local.entity.shouldFetchDetail
import io.github.tranhuuluong.kmpgithubclient.user.data.mapper.toUser
import io.github.tranhuuluong.kmpgithubclient.user.data.mapper.toUserDetail
import io.github.tranhuuluong.kmpgithubclient.user.data.mapper.toUserEntity
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.UserRemoteDataSource
import io.github.tranhuuluong.kmpgithubclient.user.domain.UserRepository
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.User
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transformLatest

class OfflineFirstUserRepository(
    private val userDao: UserDao,
    private val remoteDataSource: UserRemoteDataSource,
) : UserRepository {

    override fun getUsers(): Flow<Result<List<User>>> = flow {
        emit(StateLoading)

        if (userDao.isEmpty()) {
            val loadUserResult = loadMoreUsers()
            if (loadUserResult is DataStateError) {
                emit(loadUserResult)
                return@flow
            }
        }
        emitAll(
            userDao.getAllUsers().map { userEntities ->
                DataStateSuccess(userEntities.map { userEntity -> userEntity.toUser() })
            }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getUserDetail(userId: String): Flow<Result<UserDetail>> =
        userDao.getUserByGithubId(userId)
            .transformLatest { userEntity ->
                if (userEntity == null || userEntity.shouldFetchDetail()) {
                    emit(StateLoading)

                    when (val response = remoteDataSource.getUserDetail(userId)) {
                        is DataStateSuccess -> userDao.upsert(response.data.toUserEntity())
                        is DataStateError -> emit(response)
                    }
                } else {
                    emit(DataStateSuccess(userEntity.toUserDetail()))
                }
            }

    override suspend fun loadMoreUsers(): DataState<Unit> {
        val since = userDao.getLastUsedId()?.toInt() ?: START_INDEX
        return when (val response = remoteDataSource.getUsers(since, DEFAULT_PAGE_SIZE)) {
            is DataStateSuccess -> {
                userDao.upsert(response.data.map { it.toUserEntity() })
                DataStateSuccess(Unit)
            }

            is DataStateError -> response
        }
    }

    companion object {
        private const val START_INDEX = 1
        private const val DEFAULT_PAGE_SIZE = 20
    }
}