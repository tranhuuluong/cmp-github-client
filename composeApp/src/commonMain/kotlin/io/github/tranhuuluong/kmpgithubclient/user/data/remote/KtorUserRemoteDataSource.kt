package io.github.tranhuuluong.kmpgithubclient.user.data.remote

import io.github.tranhuuluong.kmpgithubclient.core.DataState
import io.github.tranhuuluong.kmpgithubclient.core.safeCall
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.dto.UserDetailDto
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.dto.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Remote data source implementation using Ktor for fetching user data from GitHub.
 */
class KtorUserRemoteDataSource(
    private val httpClient: HttpClient
) : UserRemoteDataSource {

    /**
     * Fetches a list of users from the GitHub API.
     *
     * @param since The starting user ID for pagination.
     * @param perPage The number of users to fetch per page.
     * @return A [DataState] containing a list of [UserDto] or an error.
     */
    override suspend fun getUsers(since: Int, perPage: Int): DataState<List<UserDto>> {
        return safeCall<List<UserDto>> {
            httpClient.get(USERS_BASE_URL) {
                parameter(Params.SINCE, since)
                parameter(Params.PER_PAGE, perPage)
            }
        }
    }

    /**
     * Fetches detailed information for a specific user from the GitHub API.
     *
     * @param githubId The GitHub user ID.
     * @return A [DataState] containing [UserDetailDto] or an error.
     */
    override suspend fun getUserDetail(githubId: String): DataState<UserDetailDto> {
        return safeCall<UserDetailDto> {
            httpClient.get("$USERS_BASE_URL/$githubId")
        }
    }

    private object Params {
        const val SINCE = "since"
        const val PER_PAGE = "per_page"
    }

    companion object {
        private const val USERS_BASE_URL = "https://api.github.com/users"
    }
}