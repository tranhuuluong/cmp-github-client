package io.github.tranhuuluong.kmpgithubclient.user.domain.model

import kotlinx.datetime.Instant

data class UserDetail(
    val user: User,
    val name: String,
    val email: String,
    val bio: String,
    val followers: Int,
    val following: Int,
    val publicRepositories: Int,
    val publicGists: Int,
    val blog: String,
    val company: String,
    val location: String,
    val createdAt: Instant,
)