package io.github.tranhuuluong.kmpgithubclient.compose_preview

import GhcTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail.UserDetailScreen
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail.UserDetailUiState

@PreviewLightDark
@Composable
private fun PreviewUserListScreen(
    @PreviewParameter(UserDetailScreenPreviewParameterProvider::class)
    state: UserDetailUiState,
) {
    GhcTheme {
        Surface {
            UserDetailScreen(
                state = state,
                onRetryClick = {},
            )
        }
    }
}