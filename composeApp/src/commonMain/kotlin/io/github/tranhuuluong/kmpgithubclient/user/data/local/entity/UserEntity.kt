package io.github.tranhuuluong.kmpgithubclient.user.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(COLUMN_ID)
    val id: Long,
    @ColumnInfo(COLUMN_GITHUB_ID)
    val githubId: String,
    @ColumnInfo(NODE_ID)
    val nodeId: String? = null,
    @ColumnInfo(AVATAR_URL)
    val avatarUrl: String? = null,
    @ColumnInfo(GRAVATAR_ID)
    val gravatarId: String? = null,
    @ColumnInfo(URL)
    val url: String? = null,
    @ColumnInfo(HTML_URL)
    val htmlUrl: String? = null,
    @ColumnInfo(FOLLOWERS_URL)
    val followersUrl: String? = null,
    @ColumnInfo(FOLLOWING_URL)
    val followingUrl: String? = null,
    @ColumnInfo(GISTS_URL)
    val gistsUrl: String? = null,
    @ColumnInfo(STARRED_URL)
    val starredUrl: String? = null,
    @ColumnInfo(SUBSCRIPTIONS_URL)
    val subscriptionsUrl: String? = null,
    @ColumnInfo(ORGANIZATIONS_URL)
    val organizationsUrl: String? = null,
    @ColumnInfo(REPOS_URL)
    val reposUrl: String? = null,
    @ColumnInfo(EVENTS_URL)
    val eventsUrl: String? = null,
    @ColumnInfo(RECEIVED_EVENTS_URL)
    val receivedEventsUrl: String? = null,
    @ColumnInfo(TYPE)
    val type: String? = null,
    @ColumnInfo(USER_VIEW_TYPE)
    val userViewType: String? = null,
    @ColumnInfo(SITE_ADMIN)
    val siteAdmin: Boolean? = null,
    @ColumnInfo(NAME)
    val name: String? = null,
    @ColumnInfo(COMPANY)
    val company: String? = null,
    @ColumnInfo(BLOG)
    val blog: String? = null,
    @ColumnInfo(LOCATION)
    val location: String? = null,
    @ColumnInfo(EMAIL)
    val email: String? = null,
    @ColumnInfo(HIREABLE)
    val hireable: Boolean? = null,
    @ColumnInfo(BIO)
    val bio: String? = null,
    @ColumnInfo(TWITTER_USERNAME)
    val twitterUsername: String? = null,
    @ColumnInfo(PUBLIC_REPOS)
    val publicRepos: Int? = null,
    @ColumnInfo(PUBLIC_GISTS)
    val publicGists: Int? = null,
    @ColumnInfo(FOLLOWERS)
    val followers: Int? = null,
    @ColumnInfo(FOLLOWING)
    val following: Int? = null,
    @ColumnInfo(CREATED_AT)
    val createdAt: Instant? = null,
    @ColumnInfo(UPDATED_AT)
    val updatedAt: Instant? = null,
) {

    companion object {
        const val TABLE_NAME = "user"
        const val COLUMN_ID = "id"
        const val COLUMN_GITHUB_ID = "github_id"
        const val NODE_ID = "node_id"
        const val AVATAR_URL = "avatar_url"
        const val GRAVATAR_ID = "gravatar_id"
        const val URL = "url"
        const val HTML_URL = "html_url"
        const val FOLLOWERS_URL = "followers_url"
        const val FOLLOWING_URL = "following_url"
        const val GISTS_URL = "gists_url"
        const val STARRED_URL = "starred_url"
        const val SUBSCRIPTIONS_URL = "subscriptions_url"
        const val ORGANIZATIONS_URL = "organizations_url"
        const val REPOS_URL = "repos_url"
        const val EVENTS_URL = "events_url"
        const val RECEIVED_EVENTS_URL = "received_events_url"
        const val TYPE = "type"
        const val USER_VIEW_TYPE = "user_view_type"
        const val SITE_ADMIN = "site_admin"
        const val NAME = "name"
        const val COMPANY = "company"
        const val BLOG = "blog"
        const val LOCATION = "location"
        const val EMAIL = "email"
        const val HIREABLE = "hireable"
        const val BIO = "bio"
        const val TWITTER_USERNAME = "twitter_username"
        const val PUBLIC_REPOS = "public_repos"
        const val PUBLIC_GISTS = "public_gists"
        const val FOLLOWERS = "followers"
        const val FOLLOWING = "following"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
    }
}

/**
 * The createdAt field only exists in the user detail endpoint
 */
fun UserEntity.shouldFetchDetail(): Boolean {
    return createdAt == null
}