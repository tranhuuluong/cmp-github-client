package io.github.tranhuuluong.kmpgithubclient.user.data.remote

import io.github.tranhuuluong.kmpgithubclient.core.DataState
import io.github.tranhuuluong.kmpgithubclient.core.safeCall
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.dto.UserDetailDto
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.dto.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class KtorUserRemoteDataSource(
    private val httpClient: HttpClient
) : UserRemoteDataSource {

    override suspend fun getUsers(since: Int, perPage: Int): DataState<List<UserDto>> {
        return safeCall<List<UserDto>> {
            httpClient.get(USERS_BASE_URL) {
                parameter("since", since)
                parameter("per_page", perPage)
            }
        }
    }

    override suspend fun getUserDetail(githubId: String): DataState<UserDetailDto> {
        return safeCall<UserDetailDto> {
            httpClient.get("$USERS_BASE_URL/$githubId")
        }
    }

    companion object {
        private const val USERS_BASE_URL = "https://api.github.com/users"
    }
}