import io.github.tranhuuluong.kmpgithubclient.user.data.local.entity.UserEntity
import io.github.tranhuuluong.kmpgithubclient.user.data.mapper.toUser
import io.github.tranhuuluong.kmpgithubclient.user.data.mapper.toUserDetail
import io.github.tranhuuluong.kmpgithubclient.user.data.mapper.toUserEntity
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.dto.UserDetailDto
import io.github.tranhuuluong.kmpgithubclient.user.data.remote.dto.UserDto
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserType
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DataMappersTest {

    @Test
    fun `UserDto toUserEntity maps fields correctly`() {
        val dto = UserDto(
            id = 1L,
            login = "john_doe",
            nodeId = "node_1",
            avatarUrl = "avatar_url",
            gravatarId = "gravatar_id",
            url = "url",
            htmlUrl = "html_url",
            followersUrl = "followers_url",
            followingUrl = "following_url",
            gistsUrl = "gists_url",
            starredUrl = "starred_url",
            subscriptionsUrl = "subscriptions_url",
            organizationsUrl = "organizations_url",
            reposUrl = "repos_url",
            eventsUrl = "events_url",
            receivedEventsUrl = "received_events_url",
            type = "User",
            userViewType = "view_type",
            siteAdmin = true
        )

        val entity = dto.toUserEntity()

        assertEquals(dto.id, entity.id)
        assertEquals(dto.login, entity.githubId)
        assertEquals(dto.avatarUrl, entity.avatarUrl)
        assertEquals(dto.type, entity.type)
        assertTrue(entity.siteAdmin!!)
    }

    @Test
    fun `UserDetailDto toUserEntity maps fields and parses dates correctly`() {
        val dto = UserDetailDto(
            id = 2L,
            login = "jane_doe",
            nodeId = "node_2",
            avatarUrl = "avatar_url_2",
            gravatarId = "gravatar_id_2",
            url = "url_2",
            htmlUrl = "html_url_2",
            followersUrl = "followers_url_2",
            followingUrl = "following_url_2",
            gistsUrl = "gists_url_2",
            starredUrl = "starred_url_2",
            subscriptionsUrl = "subscriptions_url_2",
            organizationsUrl = "organizations_url_2",
            reposUrl = "repos_url_2",
            eventsUrl = "events_url_2",
            receivedEventsUrl = "received_events_url_2",
            type = "Organization",
            userViewType = "view_type_2",
            siteAdmin = false,
            name = "Jane Doe",
            company = "Company",
            blog = "Blog",
            location = "Location",
            email = "jane@example.com",
            bio = "Bio",
            twitterUsername = "jane_twitter",
            hireable = true,
            publicRepos = 10,
            publicGists = 5,
            followers = 100,
            following = 50,
            createdAt = "2024-01-01T00:00:00Z",
            updatedAt = "2024-02-01T00:00:00Z"
        )

        val entity = dto.toUserEntity()

        assertEquals(dto.name, entity.name)
        assertEquals(Instant.parse(dto.createdAt!!), entity.createdAt)
        assertEquals(Instant.parse(dto.updatedAt!!), entity.updatedAt)
        assertEquals(dto.followers, entity.followers)
    }

    @Test
    fun `UserEntity toUser maps fields and type correctly`() {
        val entity = UserEntity(
            id = 3L,
            githubId = "jack_doe",
            avatarUrl = "avatar_url_3",
            htmlUrl = "html_url_3",
            type = "Organization",
            siteAdmin = false
        )

        val user = entity.toUser()

        assertEquals(entity.githubId, user.id)
        assertEquals(entity.avatarUrl, user.avatarUrl)
        assertEquals(UserType.Organization, user.type)
    }

    @Test
    fun `UserEntity toUserDetail maps fields and sets defaults`() {
        val entity = UserEntity(
            id = 4L,
            githubId = "jill_doe",
            avatarUrl = "avatar_url_4",
            htmlUrl = "html_url_4",
            type = "User",
            name = null,
            email = null,
            bio = null,
            followers = null,
            following = null,
            publicRepos = null,
            publicGists = null,
            blog = null,
            company = null,
            location = null,
            createdAt = null
        )

        val detail = entity.toUserDetail()

        assertEquals(entity.githubId, detail.user.id)
        assertEquals("", detail.name) // defaulted
        assertEquals(0, detail.followers) // defaulted
        assertEquals(Instant.DISTANT_PAST, detail.createdAt) // defaulted
    }
}
