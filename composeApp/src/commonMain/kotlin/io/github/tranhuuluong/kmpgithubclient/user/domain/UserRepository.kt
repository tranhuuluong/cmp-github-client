package io.github.tranhuuluong.kmpgithubclient.user.domain

import io.github.tranhuuluong.kmpgithubclient.core.DataState
import io.github.tranhuuluong.kmpgithubclient.core.Result
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.User
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

/**
 * Data layer interface for fetching users and their details
 */
interface UserRepository {
    /**
     * Returns a flow of user list results.
     *
     * @return A [Flow] emitting loading, success, or error states with a list of [User].
     */
    fun getUsers(): Flow<Result<List<User>>>

    /**
     * Returns a flow of detailed user information for the given user ID.
     *
     * @param userId The GitHub user ID.
     * @return A [Flow] emitting loading, success, or error states with [UserDetail].
     */
    fun getUserDetail(userId: String): Flow<Result<UserDetail>>

    /**
     * Loads the next page of users from the remote source.
     *
     * @return A [DataState] indicating success or error.
     */
    suspend fun loadMoreUsers(): DataState<Unit>
}