package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail

import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.core.Result
import io.github.tranhuuluong.kmpgithubclient.core.StateLoading
import io.github.tranhuuluong.kmpgithubclient.core.util.DateTimeFormatter
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserDetail
import kotlinx.datetime.TimeZone

fun Result<UserDetail>.toUiState(currentTimeZone: TimeZone) = when (this) {
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
            joinedDate = DateTimeFormatter.formatToLocalDate(createdAt, currentTimeZone),
            email = email,
            bio = bio,
            blog = blog,
        )
    }

    else -> UserDetailUiState.Loading
}
