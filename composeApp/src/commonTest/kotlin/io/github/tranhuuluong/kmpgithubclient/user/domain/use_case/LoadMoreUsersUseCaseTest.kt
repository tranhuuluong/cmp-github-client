package io.github.tranhuuluong.kmpgithubclient.user.domain.use_case

import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LoadMoreUsersUseCaseTest {

    private lateinit var useCase: LoadMoreUsersUseCase

    private lateinit var userRepository: FakeUserRepository

    @BeforeTest
    fun setUp() {
        userRepository = FakeUserRepository()
        useCase = LoadMoreUsersUseCase(userRepository)
    }

    @Test
    fun `return Success when load more users successfully`() = runTest {
        userRepository.emitLoadMoreUsersState(DataStateSuccess(Unit))
        assertTrue(useCase() is DataStateSuccess)
    }

    @Test
    fun `return Error when load more users failed`() = runTest {
        val exception = Exception("Something went wrong!")
        userRepository.emitLoadMoreUsersState(
            DataStateError(Exception("Something went wrong!"))
        )
        assertEquals(exception.message, (useCase() as DataStateError).exception.message)

    }
}