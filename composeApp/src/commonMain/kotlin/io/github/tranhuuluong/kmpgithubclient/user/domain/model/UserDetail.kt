package io.github.tranhuuluong.kmpgithubclient.user.domain.model

data class UserDetail(
    val user: User,
    val followers: Int,
    val following: Int,
    val publicRepositories: Int,
    val publicGists: Int,
    val blog: String,
    val company: String,
    val location: String,
    val createdAt: String
)