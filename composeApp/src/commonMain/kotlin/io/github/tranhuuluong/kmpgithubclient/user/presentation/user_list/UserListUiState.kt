package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list

sealed interface UserListUiState {

    data object Loading : UserListUiState

    data class Success(val users: List<UserUiModel>) : UserListUiState
}

data class UserUiModel(
    val id: String,
    val name: String = "",
    val avatarUrl: String = "",
    val profileUrl: String = "",
)