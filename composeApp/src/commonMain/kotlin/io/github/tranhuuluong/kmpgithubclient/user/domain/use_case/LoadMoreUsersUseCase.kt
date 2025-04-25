package io.github.tranhuuluong.kmpgithubclient.user.domain.use_case

import io.github.tranhuuluong.kmpgithubclient.user.domain.UserRepository

class LoadMoreUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.loadMoreUsers()
}