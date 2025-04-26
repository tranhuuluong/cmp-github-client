package io.github.tranhuuluong.kmpgithubclient.user.data

import app.cash.turbine.test
import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.core.StateLoading
import io.github.tranhuuluong.kmpgithubclient.user.data.local.entity.UserEntity
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OfflineFirstUserRepositoryTest {

    private lateinit var repository: OfflineFirstUserRepository
    private lateinit var userDao: FakeUserDao
    private lateinit var userRemoteDataSource: FakeUserRemoteDataSource

    @BeforeTest
    fun setUp() {
        userDao = FakeUserDao()
        userRemoteDataSource = FakeUserRemoteDataSource()
        repository = OfflineFirstUserRepository(
            userDao = userDao,
            remoteDataSource = userRemoteDataSource
        )
    }

    @Test
    fun `getUsers emits loading and success when local is empty and remote fetch succeeds`() =
        runTest {
            repository.getUsers().test {
                assertTrue(awaitItem() is StateLoading)
                val result = awaitItem()
                assertTrue(result is DataStateSuccess)
                val users = result.data
                assertEquals(2, users.size)
                assertEquals("john_doe_0", users[0].id)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getUsers emits loading and error when local is empty and remote fetch fails`() = runTest {
        userRemoteDataSource.shouldReturnError = true

        repository.getUsers().test {
            assertTrue(awaitItem() is StateLoading)
            assertTrue(awaitItem() is DataStateError)

            awaitComplete()
        }
    }

    @Test
    fun `getUsers emits loading and local users when local has data`() = runTest {
        val userEntity = UserEntity(
            id = 1,
            githubId = "john_doe_1",
            avatarUrl = "avatarUrl_1",
        )
        userDao.upsert(userEntity)

        repository.getUsers().test {
            assertTrue(awaitItem() is StateLoading)
            val result = awaitItem()
            assertTrue(result is DataStateSuccess)
            val users = result.data
            assertEquals(1, users.size)
            assertEquals("john_doe_1", users[0].id)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getUserDetail emits loading and success when detail needs fetching and remote succeeds`() =
        runTest {
            val userEntity = UserEntity(
                id = 0,
                githubId = "login_0",
                avatarUrl = "avatarUrl_0",
            )
            userDao.upsert(userEntity)

            repository.getUserDetail("login_0").test {
                assertTrue(awaitItem() is StateLoading)
                val result = awaitItem()
                assertTrue(result is DataStateSuccess)
                val userDetail = result.data
                assertEquals("login_0", userDetail.user.id)
                assertEquals("name_0", userDetail.name)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getUserDetail emits loading and error when detail needs fetching and remote fails`() =
        runTest {
            val userEntity = UserEntity(
                id = 0,
                githubId = "login_0",
                avatarUrl = "avatarUrl_0",
            )
            userDao.upsert(userEntity)
            userRemoteDataSource.shouldReturnError = true

            repository.getUserDetail("login_0").test {
                assertTrue(awaitItem() is StateLoading)
                val result = awaitItem()
                assertTrue(result is DataStateError)
                val error = result.exception
                assertEquals("Failed to fetch user detail", error.message)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getUserDetail emits success when local detail exists and no fetch needed`() = runTest {
        val userEntity = UserEntity(
            id = 0,
            githubId = "login_0",
            avatarUrl = "avatarUrl_0",
            name = "name_0",
            company = "company_0",
            blog = "blog_0",
            location = "location_0",
            email = "email_0",
            bio = "bio_0",
            twitterUsername = "twitterUsername_0",
            createdAt = Instant.parse("2020-01-01T00:00:00Z")
        )
        userDao.upsert(userEntity)

        repository.getUserDetail("login_0").test {
            val result = awaitItem()
            assertTrue(result is DataStateSuccess)
            val userDetail = result.data
            assertEquals("login_0", userDetail.user.id)
            assertEquals("name_0", userDetail.name)

            cancelAndIgnoreRemainingEvents()
        }
    }

}