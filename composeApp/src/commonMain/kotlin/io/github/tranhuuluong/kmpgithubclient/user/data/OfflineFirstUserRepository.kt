package io.github.tranhuuluong.kmpgithubclient.user.data

import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.core.Result
import io.github.tranhuuluong.kmpgithubclient.core.StateLoading
import io.github.tranhuuluong.kmpgithubclient.core.map
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.UserRemoteDataSource
import io.github.tranhuuluong.kmpgithubclient.user.domain.UserRepository
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.User
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserDetail
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.seconds

class OfflineFirstUserRepository(
    private val remoteDataSource: UserRemoteDataSource,
) : UserRepository {

    private val fakeUsers = List(20) {
        val userId = "user$it"
        User(
            id = userId,
            name = "User $it",
            profileUrl = "https://github.com/$userId",
            avatarUrl = "https://avatars.githubusercontent.com/u/$it",
            type = UserType.entries.random()
        )
    }

    override fun getUsers(): Flow<Result<List<User>>> = flow {
        emit(StateLoading)
        emit(
            remoteDataSource.getUsers(1, 20).map { usersResponse ->
                usersResponse.map { userDto ->
                    User(
                        id = userDto.id.toString(),
                        name = userDto.login,
                        profileUrl = userDto.htmlUrl.orEmpty(),
                        avatarUrl = userDto.avatarUrl.orEmpty(),
                        type = when (userDto.type) {
                            "User" -> UserType.User
                            "Organization" -> UserType.Organization
                            else -> UserType.Unknown
                        }
                    )
                }
            }
        )
    }

    override fun getUserDetail(id: String): Flow<Result<UserDetail>> {
        return flow {
            emit(StateLoading)
            delay(2.seconds)
            emit(
                DataStateSuccess(
                    UserDetail(
                        user = fakeUsers.first { it.id == id },
                        followers = 0,
                        following = 0,
                        publicRepositories = 0,
                        publicGists = 0,
                        blog = "",
                        company = "",
                        location = "",
                        createdAt = "",
                    )
                )
            )
        }
    }
}