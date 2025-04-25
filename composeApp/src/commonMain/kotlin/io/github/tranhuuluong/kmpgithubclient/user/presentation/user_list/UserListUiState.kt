package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list

sealed interface UserListUiState {
    data object Empty : UserListUiState

    data object Loading : UserListUiState

    data class Error(val message: String) : UserListUiState

    data class Success(val users: List<UserUiModel>) : UserListUiState
}

sealed interface LoadMoreUiState {
    data object Idle : LoadMoreUiState

    data object Loading : LoadMoreUiState

    data class Error(val message: String) : LoadMoreUiState
}

data class UserUiModel(
    val id: String,
    val avatarUrl: String = "",
    val profileUrl: String = "",
)