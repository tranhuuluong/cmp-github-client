import app.cash.turbine.test
import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.core.StateLoading
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.User
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserType
import io.github.tranhuuluong.kmpgithubclient.user.domain.use_case.FakeUserRepository
import io.github.tranhuuluong.kmpgithubclient.user.domain.use_case.GetUserListUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUserListUseCaseTest {

    private lateinit var useCase: GetUserListUseCase

    private lateinit var userRepository: FakeUserRepository

    @BeforeTest
    fun setUp() {
        userRepository = FakeUserRepository()
        useCase = GetUserListUseCase(userRepository)
    }

    @Test
    fun `emits loading and success states when user list is retrieved successfully`() = runTest {
        val users = listOf(
            User(id = "1", avatarUrl = "avatar1", profileUrl = "profile1", type = UserType.User),
            User(id = "2", avatarUrl = "avatar2", profileUrl = "profile2", type = UserType.User)
        )
        userRepository.emitUsersState(
            StateLoading,
            DataStateSuccess(users),
        )

        useCase().test {
            assertEquals(StateLoading, awaitItem())
            assertEquals(DataStateSuccess(users), awaitItem())

            awaitComplete()
        }
    }

    @Test
    fun `emits loading and error states when user retrieval fails`() = runTest {
        val exception = Exception("Something went wrong!")

        userRepository.emitUsersState(
            StateLoading,
            DataStateError(Exception("Something went wrong!"))
        )

        useCase().test {
            assertEquals(StateLoading, awaitItem())
            assertEquals(exception.message, (awaitItem() as DataStateError).exception.message)

            awaitComplete()
        }
    }
}