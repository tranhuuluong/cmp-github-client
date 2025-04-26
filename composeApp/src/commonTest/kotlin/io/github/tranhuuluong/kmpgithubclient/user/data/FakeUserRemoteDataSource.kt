package io.github.tranhuuluong.kmpgithubclient.user.data

import io.github.tranhuuluong.kmpgithubclient.core.DataState
import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.UserRemoteDataSource
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.dto.UserDetailDto
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.dto.UserDto

class FakeUserRemoteDataSource : UserRemoteDataSource {

    var shouldReturnError = false

    private val fakeUsers = List(2) {
        UserDto(
            id = it.toLong(),
            login = "john_doe_$it",
            nodeId = "nodeId_$it",
            avatarUrl = "avatarUrl_$it",
            gravatarId = "gravatarId_$it",
            url = "url_$it",
            htmlUrl = "htmlUrl_$it",
            followersUrl = "followersUrl_$it",
            followingUrl = "followingUrl_$it",
            gistsUrl = "gistsUrl_$it",
            starredUrl = "starredUrl_$it",
            subscriptionsUrl = "subscriptionsUrl_$it",
            organizationsUrl = "organizationsUrl_$it",
            reposUrl = "reposUrl_$it",
            eventsUrl = "eventsUrl_$it",
            receivedEventsUrl = "receivedEventsUrl_$it",
            type = "User",
            userViewType = "userViewType_$it",
            siteAdmin = false
        )
    }

    private val fakeUserDetails = List(2) {
        UserDetailDto(
            id = it.toLong(),
            login = "login_$it",
            nodeId = "nodeId_$it",
            avatarUrl = "avatarUrl_$it",
            gravatarId = "gravatarId_$it",
            url = "url_$it",
            htmlUrl = "htmlUrl_$it",
            followersUrl = "followersUrl_$it",
            followingUrl = "followingUrl_$it",
            gistsUrl = "gistsUrl_$it",
            starredUrl = "starredUrl_$it",
            subscriptionsUrl = "subscriptionsUrl_$it",
            organizationsUrl = "organizationsUrl_$it",
            reposUrl = "reposUrl_$it",
            eventsUrl = "eventsUrl_$it",
            receivedEventsUrl = "receivedEventsUrl_$it",
            userViewType = "userViewType_$it",
            name = "name_$it",
            company = "company_$it",
            blog = "blog_$it",
            location = "location_$it",
            email = "email_$it",
            bio = "bio_$it",
            twitterUsername = "twitterUsername_$it",
            type = "type_$it",
            hireable = true,
            siteAdmin = true,
            publicRepos = 10,
            publicGists = 10,
            followers = 10,
            following = 10,
            createdAt = "2020-01-01T00:00:00Z",
            updatedAt = "2021-01-01T00:00:00Z"
        )
    }

    override suspend fun getUsers(since: Int, perPage: Int): DataState<List<UserDto>> {
        return if (shouldReturnError) {
            DataStateError(Exception("Failed to fetch users"))
        } else {
            DataStateSuccess(fakeUsers)
        }
    }

    override suspend fun getUserDetail(githubId: String): DataState<UserDetailDto> {
        return if (shouldReturnError) {
            DataStateError(Exception("Failed to fetch user detail"))
        } else {
            DataStateSuccess(fakeUserDetails.first { it.login == githubId })
        }
    }
}