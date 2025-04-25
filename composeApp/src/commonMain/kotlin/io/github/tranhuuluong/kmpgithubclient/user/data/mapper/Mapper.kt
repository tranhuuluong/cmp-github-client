package io.github.tranhuuluong.kmpgithubclient.user.data.mapper

import io.github.tranhuuluong.kmpgithubclient.user.data.local.entity.UserEntity
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.dto.UserDetailDto
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.dto.UserDto
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.User
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserDetail
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserType

fun UserDto.toUserEntity(): UserEntity = UserEntity(
    id = id,
    githubId = login,
    nodeId = nodeId,
    avatarUrl = avatarUrl,
    gravatarId = gravatarId,
    url = url,
    htmlUrl = htmlUrl,
    followersUrl = followersUrl,
    followingUrl = followingUrl,
    gistsUrl = gistsUrl,
    starredUrl = starredUrl,
    subscriptionsUrl = subscriptionsUrl,
    organizationsUrl = organizationsUrl,
    reposUrl = reposUrl,
    eventsUrl = eventsUrl,
    receivedEventsUrl = receivedEventsUrl,
    type = type,
    userViewType = userViewType,
    siteAdmin = siteAdmin,
)

fun UserDetailDto.toUserEntity(): UserEntity = UserEntity(
    id = id,
    githubId = login,
    nodeId = nodeId,
    avatarUrl = avatarUrl,
    gravatarId = gravatarId,
    url = url,
    htmlUrl = htmlUrl,
    followersUrl = followersUrl,
    followingUrl = followingUrl,
    gistsUrl = gistsUrl,
    starredUrl = starredUrl,
    subscriptionsUrl = subscriptionsUrl,
    organizationsUrl = organizationsUrl,
    reposUrl = reposUrl,
    eventsUrl = eventsUrl,
    receivedEventsUrl = receivedEventsUrl,
    type = type,
    userViewType = userViewType,
    siteAdmin = siteAdmin,
    name = name,
    company = company,
    blog = blog,
    location = location,
    email = email,
    hireable = hireable,
    bio = bio,
    twitterUsername = twitterUsername,
    publicRepos = publicRepos,
    publicGists = publicGists,
    followers = followers,
    following = following,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun UserEntity.toUser(): User = User(
    id = githubId,
    avatarUrl = avatarUrl.orEmpty(),
    profileUrl = htmlUrl.orEmpty(),
    type = when (type) {
        "User" -> UserType.User
        "Organization" -> UserType.Organization
        else -> UserType.Unknown
    }
)

fun UserEntity.toUserDetail(): UserDetail = UserDetail(
    user = toUser(),
    name = name.orEmpty(),
    email = email.orEmpty(),
    followers = followers ?: 0,
    following = following ?: 0,
    publicRepositories = publicRepos ?: 0,
    publicGists = publicGists ?: 0,
    blog = blog.orEmpty(),
    company = company.orEmpty(),
    location = location.orEmpty(),
    createdAt = createdAt.orEmpty(),
)