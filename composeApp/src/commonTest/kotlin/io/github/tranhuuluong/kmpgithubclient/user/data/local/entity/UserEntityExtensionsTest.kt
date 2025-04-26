package io.github.tranhuuluong.kmpgithubclient.user.data.local.entity

import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserEntityExtensionsTest {

    @Test
    fun `shouldFetchDetail returns true when createdAt is null`() {
        val userEntity = UserEntity(
            id = 1,
            githubId = "john_doe",
            createdAt = null
        )

        assertTrue(userEntity.shouldFetchDetail())
    }

    @Test
    fun `shouldFetchDetail returns false when createdAt is not null`() {
        val userEntity = UserEntity(
            id = 1,
            githubId = "john_doe",
            createdAt = Instant.fromEpochMilliseconds(0)
        )

        assertFalse(userEntity.shouldFetchDetail())
    }
}