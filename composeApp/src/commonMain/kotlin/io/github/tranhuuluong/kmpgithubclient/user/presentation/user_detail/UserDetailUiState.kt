package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail

/**
 * Represents the UI state for displaying user details.
 */
sealed interface UserDetailUiState {
    /**
     * Loading state while user details are being fetched.
     */
    data object Loading : UserDetailUiState

    /**
     * Error state containing a message when fetching user details fails.
     *
     * @param message Error message describing the failure.
     */
    data class Error(val message: String) : UserDetailUiState

    /**
     * Success state containing detailed user information.
     */
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
        val joinedDate: String = "",
        val email: String = "",
        val bio: String = "",
        val blog: String = ""
    ) : UserDetailUiState
}