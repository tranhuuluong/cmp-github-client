import app.cash.turbine.test
import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.core.util.TimeProvider
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.User
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserDetail
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserType
import io.github.tranhuuluong.kmpgithubclient.user.domain.use_case.FakeUserRepository
import io.github.tranhuuluong.kmpgithubclient.user.domain.use_case.GetUserDetailUseCase
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail.UserDetailUiState
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail.UserDetailViewModel
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserDetailViewModelTest {

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase
    private lateinit var timeProvider: TimeProvider
    private lateinit var viewModel: UserDetailViewModel

    private val userId = "user123"

    @BeforeTest
    fun setup() {
        fakeUserRepository = FakeUserRepository()
        getUserDetailUseCase = GetUserDetailUseCase(fakeUserRepository)
        timeProvider = object : TimeProvider {
            override fun systemDefaultTimezone(): TimeZone = TimeZone.UTC
        }
        viewModel = UserDetailViewModel(
            userId = userId,
            getUserDetailUseCase = getUserDetailUseCase,
            timeProvider = timeProvider
        )
    }

    @Test
    fun `userDetailUiState emits Success when use case returns success`() = runTest {
        val userDetail = UserDetail(
            user = User(
                id = userId,
                avatarUrl = "avatar",
                profileUrl = "profile",
                type = UserType.User
            ),
            name = "John Doe",
            followers = 10,
            following = 5,
            publicRepositories = 3,
            publicGists = 2,
            company = "Company",
            location = "Location",
            createdAt = Instant.parse("2024-01-01T00:00:00Z"),
            email = "john@example.com",
            bio = "Bio",
            blog = "Blog"
        )
        fakeUserRepository.emitUserDetailState(DataStateSuccess(userDetail))

        viewModel.userDetailUiState.test {
            assertTrue(awaitItem() is UserDetailUiState.Loading) // initial state
            val state = awaitItem()
            assertTrue(state is UserDetailUiState.Success)
            assertEquals("John Doe", state.userName)
            assertEquals("2024-01-01", state.joinedDate) // formatted date
        }
    }

    @Test
    fun `userDetailUiState emits Error when use case returns error`() = runTest {
        fakeUserRepository.emitUserDetailState(DataStateError(Exception("Detail error")))

        viewModel.userDetailUiState.test {
            assertTrue(awaitItem() is UserDetailUiState.Loading) // initial state
            val state = awaitItem()
            assertTrue(state is UserDetailUiState.Error)
            assertEquals("Detail error", state.message)
        }
    }

    @Test
    fun `retry triggers user detail refresh`() = runTest {
        fakeUserRepository.emitUserDetailState(
            DataStateError(Exception("Detail error")),
            DataStateSuccess(
                UserDetail(
                    user = User(userId, "avatar", "profile", UserType.User),
                    name = "John Updated",
                    followers = 2,
                    following = 2,
                    publicRepositories = 2,
                    publicGists = 2,
                    company = "",
                    location = "",
                    createdAt = Instant.parse("2024-01-01T00:00:00Z"),
                    email = "",
                    bio = "",
                    blog = ""
                )
            )
        )

        viewModel.userDetailUiState.test {
            assertTrue(awaitItem() is UserDetailUiState.Loading) // initial state
            val firstState = awaitItem()
            assertTrue(firstState is UserDetailUiState.Error)

            viewModel.retry() // Trigger retry

            val refreshedState = awaitItem()
            assertTrue(refreshedState is UserDetailUiState.Success)
            assertEquals("John Updated", refreshedState.userName)
        }
    }
}