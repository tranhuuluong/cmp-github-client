package io.github.tranhuuluong.kmpgithubclient.user.domain.use_case

import io.github.tranhuuluong.kmpgithubclient.core.DataState
import io.github.tranhuuluong.kmpgithubclient.core.Result
import io.github.tranhuuluong.kmpgithubclient.user.domain.UserRepository
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.User
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepository : UserRepository {

    private lateinit var usersFlow: Flow<Result<List<User>>>
    private lateinit var userDetailFlow: Flow<Result<UserDetail>>
    private lateinit var loadMoreUserResult: DataState<Unit>

    override fun getUsers(): Flow<Result<List<User>>> = usersFlow

    override fun getUserDetail(userId: String): Flow<Result<UserDetail>> = userDetailFlow

    override suspend fun loadMoreUsers(): DataState<Unit> = loadMoreUserResult

    fun emitUsersState(
        vararg states: Result<List<User>>
    ) {
        usersFlow = flow {
            for (state in states) {
                emit(state)
            }
        }
    }

    fun emitUserDetailState(
        vararg states: Result<UserDetail>
    ) {
        userDetailFlow = flow {
            for (state in states) {
                emit(state)
            }
        }
    }

    fun emitLoadMoreUsersState(state: DataState<Unit>) {
        loadMoreUserResult = state
    }

}