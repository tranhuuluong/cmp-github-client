package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list

/**
 * Represents the UI state for displaying a list of users.
 */
sealed interface UserListUiState {
    /**
     * Empty state when no users are available.
     */
    data object Empty : UserListUiState

    /**
     * Loading state while the user list is being fetched.
     */
    data object Loading : UserListUiState

    /**
     * Error state containing a message when fetching the user list fails.
     *
     * @param message Error message describing the failure.
     */
    data class Error(val message: String) : UserListUiState

    /**
     * Success state containing a list of user UI models.
     */
    data class Success(val users: List<UserUiModel>) : UserListUiState
}

/**
 * Represents the UI state for loading more users.
 */
sealed interface LoadMoreUiState {
    /**
     * Idle state when no loading is in progress.
     */
    data object Idle : LoadMoreUiState

    /**
     * Loading state while fetching more users.
     */
    data object Loading : LoadMoreUiState

    /**
     * Error state containing a message when loading more users fails.
     *
     * @param message Error message describing the failure.
     */
    data class Error(val message: String) : LoadMoreUiState
}

data class UserUiModel(
    val id: String,
    val avatarUrl: String = "",
    val profileUrl: String = "",
)