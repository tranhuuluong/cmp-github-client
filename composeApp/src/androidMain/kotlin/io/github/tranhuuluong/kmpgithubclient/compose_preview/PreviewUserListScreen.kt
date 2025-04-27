package io.github.tranhuuluong.kmpgithubclient.compose_preview

import GhcTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.LoadMoreUiState
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.UserListScreen
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.UserListUiState

@PreviewLightDark
@Composable
private fun PreviewUserListScreen(
    @PreviewParameter(UserListScreenPreviewParameterProvider::class)
    states: Pair<UserListUiState, LoadMoreUiState>,
) {
    GhcTheme {
        Surface {
            val (userListUiState, loadMoreUiState) = states
            UserListScreen(
                state = userListUiState,
                loadMoreState = loadMoreUiState,
                onUserClick = {},
                onLoadMore = {},
                onRetryClick = {},
            )
        }
    }
}