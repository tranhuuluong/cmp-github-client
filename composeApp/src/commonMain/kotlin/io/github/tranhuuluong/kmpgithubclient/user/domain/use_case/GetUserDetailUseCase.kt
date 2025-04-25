package io.github.tranhuuluong.kmpgithubclient.user.domain.use_case

import io.github.tranhuuluong.kmpgithubclient.core.Result
import io.github.tranhuuluong.kmpgithubclient.user.domain.UserRepository
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

class GetUserDetailUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(userId: String): Flow<Result<UserDetail>> {
        return userRepository.getUserDetail(userId)
    }
}