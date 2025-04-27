package io.github.tranhuuluong.kmpgithubclient.compose_preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.LoadMoreUiState
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.UserListUiState
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.UserUiModel

class UserListScreenPreviewParameterProvider :
    PreviewParameterProvider<Pair<UserListUiState, LoadMoreUiState>> {

    private val users = List(20) {
        UserUiModel(id = "User $it", profileUrl = "https://github.com/user$it")
    }

    override val values: Sequence<Pair<UserListUiState, LoadMoreUiState>>
        get() = sequenceOf(
            UserListUiState.Loading to LoadMoreUiState.Idle,
            UserListUiState.Success(users) to LoadMoreUiState.Loading,
            UserListUiState.Success(users) to LoadMoreUiState.Error("Error"),
            UserListUiState.Error("Error") to LoadMoreUiState.Idle
        )
}