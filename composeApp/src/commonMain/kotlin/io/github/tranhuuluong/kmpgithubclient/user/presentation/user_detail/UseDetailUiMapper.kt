package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail

import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.core.Result
import io.github.tranhuuluong.kmpgithubclient.core.StateLoading
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserDetail

fun Result<UserDetail>.toUiState() = when (this) {
    is StateLoading -> UserDetailUiState.Loading
    is DataStateError -> UserDetailUiState.Error(exception.message.orEmpty())
    is DataStateSuccess -> with(data) {
        UserDetailUiState.Success(
            id = user.id,
            avatarUrl = user.avatarUrl,
            profileUrl = user.profileUrl,
            userName = name,
            followers = followers,
            following = following,
            publicGists = publicGists,
            publicRepos = publicRepositories,
            company = company,
            location = location,
            joinDate = createdAt,
            email = email,
            blog = blog,
        )
    }

    else -> UserDetailUiState.Loading
}
