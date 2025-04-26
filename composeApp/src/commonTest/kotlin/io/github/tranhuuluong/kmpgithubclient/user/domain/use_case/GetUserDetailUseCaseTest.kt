package io.github.tranhuuluong.kmpgithubclient.user.domain.use_case

import app.cash.turbine.test
import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.core.StateLoading
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.User
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserDetail
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserType
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUserDetailUseCaseTest {

    private lateinit var useCase: GetUserDetailUseCase

    private lateinit var userRepository: FakeUserRepository

    @BeforeTest
    fun setUp() {
        userRepository = FakeUserRepository()
        useCase = GetUserDetailUseCase(userRepository = userRepository)
    }

    @Test
    fun `emits loading and success states when user detail is retrieved successfully`() = runTest {
        val userDetail = UserDetail(
            user = User("1", "", "", UserType.Organization),
            name = "",
            company = "",
            blog = "",
            location = "",
            email = "",
            bio = "",
            following = 10,
            followers = 5,
            publicRepositories = 5,
            publicGists = 10,
            createdAt = Instant.fromEpochMilliseconds(0)
        )
        userRepository.emitUserDetailState(
            StateLoading,
            DataStateSuccess(userDetail)
        )

        useCase("1").test {
            assertEquals(StateLoading, awaitItem())
            assertEquals(DataStateSuccess(userDetail), awaitItem())

            awaitComplete()
        }
    }

    @Test
    fun `emits loading and error states when user detail retrieval fail`() = runTest {
        val exception = Exception("Something went wrong!")

        userRepository.emitUserDetailState(
            StateLoading,
            DataStateError(Exception("Something went wrong!"))
        )

        useCase("1").test {
            assertEquals(StateLoading, awaitItem())
            assertEquals(exception.message, (awaitItem() as DataStateError).exception.message)

            awaitComplete()
        }
    }
}