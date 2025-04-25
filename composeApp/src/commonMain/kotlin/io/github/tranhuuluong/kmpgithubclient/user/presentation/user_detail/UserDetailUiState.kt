package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail

sealed interface UserDetailUiState {
    data object Loading : UserDetailUiState

    data class Error(val message: String) : UserDetailUiState

    data class Success(
        val id: String,
        val userName: String = "",
        val avatarUrl: String = "",
        val profileUrl: String = "",
        val followers: Int = 0,
        val following: Int = 0,
        val publicGists: Int = 0,
        val publicRepos: Int = 0,
        val company: String = "",
        val location: String = "",
        val joinDate: String = "",
        val email: String = "",
        val blog: String = ""
    ) : UserDetailUiState
}