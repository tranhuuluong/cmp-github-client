package io.github.tranhuuluong.kmpgithubclient.user.data.remote

import io.github.tranhuuluong.kmpgithubclient.core.DataState
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.dto.UserDetailDto
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.dto.UserDto

interface UserRemoteDataSource {

    suspend fun getUsers(since: Int, perPage: Int): DataState<List<UserDto>>

    suspend fun getUserDetail(githubId: String): DataState<UserDetailDto>
}