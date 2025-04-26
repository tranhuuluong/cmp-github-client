package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list

import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.core.Result
import io.github.tranhuuluong.kmpgithubclient.core.StateLoading
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.User

fun Result<List<User>>.toUiState(): UserListUiState = when (this) {
    is StateLoading -> UserListUiState.Loading
    is DataStateSuccess -> UserListUiState.Success(data.toUserUiModels())
    is DataStateError -> UserListUiState.Error(exception.message.orEmpty())
    else -> UserListUiState.Empty
}

fun List<User>.toUserUiModels(): List<UserUiModel> = map { user -> user.toUserUiModel() }

fun User.toUserUiModel(): UserUiModel = UserUiModel(
    id = id,
    avatarUrl = avatarUrl,
    profileUrl = profileUrl,
)