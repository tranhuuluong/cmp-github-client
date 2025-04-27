package io.github.tranhuuluong.kmpgithubclient.compose_preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail.UserDetailUiState
import kotlin.random.Random

class UserDetailScreenPreviewParameterProvider : PreviewParameterProvider<UserDetailUiState> {

    override val values: Sequence<UserDetailUiState>
        get() = sequenceOf(
            UserDetailUiState.Loading,
            UserDetailUiState.Error("Error"),
            UserDetailUiState.Success(
                id = "tranhuuluong",
                userName = "Luong Tran",
                followers = Random.nextInt(1, 1000),
                following = Random.nextInt(1, 1000),
                publicGists = Random.nextInt(1, 1000),
                publicRepos = Random.nextInt(1, 1000),
                location = "Ho Chi Minh city, Vietnam",
                email = "tranluong025@gmail.com",
                joinedDate = "2025-01-01",
                profileUrl = "https://github.com/tranhuuluong",
                blog = "https://github.com/tranhuuluong",
                bio = "I'm a software engineer"
            )
        )
}