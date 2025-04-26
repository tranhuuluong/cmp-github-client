package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail

import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.core.StateIdle
import io.github.tranhuuluong.kmpgithubclient.core.StateLoading
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.User
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserDetail
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserType
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserDetailUiMapperTest {

    private val timeZone = TimeZone.UTC

    @Test
    fun `toUiState maps StateLoading to Loading`() {
        val result = StateLoading
        val uiState = result.toUiState(timeZone)

        assertTrue(uiState is UserDetailUiState.Loading)
    }

    @Test
    fun `toUiState maps DataStateError to Error with message`() {
        val exceptionMessage = "Something went wrong"
        val result = DataStateError(Exception(exceptionMessage))
        val uiState = result.toUiState(timeZone)

        assertTrue(uiState is UserDetailUiState.Error)
        assertEquals(exceptionMessage, uiState.message)
    }

    @Test
    fun `toUiState maps DataStateSuccess to Success with formatted data`() {
        val createdAt = Instant.parse("2025-04-26T10:15:30Z")
        val userDetail = UserDetail(
            user = User(
                id = "user123",
                avatarUrl = "avatar_url",
                profileUrl = "profile_url",
                type = UserType.User
            ),
            name = "John Doe",
            followers = 100,
            following = 50,
            publicRepositories = 10,
            publicGists = 5,
            company = "Company",
            location = "Location",
            createdAt = createdAt,
            email = "john@example.com",
            bio = "Bio",
            blog = "Blog"
        )

        val result = DataStateSuccess(userDetail)
        val uiState = result.toUiState(timeZone)

        assertTrue(uiState is UserDetailUiState.Success)

        assertEquals("user123", uiState.id)
        assertEquals("avatar_url", uiState.avatarUrl)
        assertEquals("profile_url", uiState.profileUrl)
        assertEquals("John Doe", uiState.userName)
        assertEquals(100, uiState.followers)
        assertEquals(50, uiState.following)
        assertEquals(10, uiState.publicRepos)
        assertEquals(5, uiState.publicGists)
        assertEquals("Company", uiState.company)
        assertEquals("Location", uiState.location)
        assertEquals("2025-04-26", uiState.joinedDate) // formatted date
        assertEquals("john@example.com", uiState.email)
        assertEquals("Bio", uiState.bio)
        assertEquals("Blog", uiState.blog)
    }

    @Test
    fun `toUiState returns Loading for unknown state`() {
        val unknownState = StateIdle
        val uiState = unknownState.toUiState(timeZone)

        assertTrue(uiState is UserDetailUiState.Loading)
    }
}
