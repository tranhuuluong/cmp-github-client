package io.github.tranhuuluong.kmpgithubclient.user.domain.model

data class User(
    val id: String,
    val avatarUrl: String,
    val profileUrl: String,
    val type: UserType
)