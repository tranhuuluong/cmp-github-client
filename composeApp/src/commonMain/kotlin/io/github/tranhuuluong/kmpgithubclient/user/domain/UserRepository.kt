package io.github.tranhuuluong.kmpgithubclient.user.domain

import io.github.tranhuuluong.kmpgithubclient.core.DataState
import io.github.tranhuuluong.kmpgithubclient.core.Result
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.User
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<Result<List<User>>>

    fun getUserDetail(id: String): Flow<Result<UserDetail>>

    suspend fun loadMoreUsers(): DataState<Unit>
}