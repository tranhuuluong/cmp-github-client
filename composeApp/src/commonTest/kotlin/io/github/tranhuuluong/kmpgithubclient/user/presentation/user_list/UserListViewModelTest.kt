import app.cash.turbine.test
import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.User
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserType
import io.github.tranhuuluong.kmpgithubclient.user.domain.use_case.FakeUserRepository
import io.github.tranhuuluong.kmpgithubclient.user.domain.use_case.GetUserListUseCase
import io.github.tranhuuluong.kmpgithubclient.user.domain.use_case.LoadMoreUsersUseCase
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.LoadMoreUiState
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.UserListUiState
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.UserListViewModel
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserListViewModelTest {

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var getUserListUseCase: GetUserListUseCase
    private lateinit var loadMoreUsersUseCase: LoadMoreUsersUseCase
    private lateinit var viewModel: UserListViewModel

    @BeforeTest
    fun setup() {
        fakeUserRepository = FakeUserRepository()
        getUserListUseCase = GetUserListUseCase(fakeUserRepository)
        loadMoreUsersUseCase = LoadMoreUsersUseCase(fakeUserRepository)
        viewModel = UserListViewModel(getUserListUseCase, loadMoreUsersUseCase)
    }

    @Test
    fun `userListState emits Success on initial load`() = runTest {
        val users = listOf(
            User(
                id = "1",
                avatarUrl = "avatar1",
                profileUrl = "profile1",
                type = UserType.User
            )
        )
        fakeUserRepository.emitUsersState(DataStateSuccess(users))

        viewModel.userListState.test {
            skipItems(1) // StateEmpty
            val state = awaitItem()
            assertTrue(state is UserListUiState.Success)
            val user = state.users.first()
            assertEquals("1", user.id)
            assertEquals("avatar1", user.avatarUrl)
            assertEquals("profile1", user.profileUrl)
        }
    }

    @Test
    fun `userListState emits Error when repository emits error`() = runTest {
        fakeUserRepository.emitUsersState(DataStateError(Exception("Failed to load")))

        viewModel.userListState.test {
            skipItems(1) // StateEmpty
            val state = awaitItem()
            assertTrue(state is UserListUiState.Error)
            assertEquals("Failed to load", state.message)
        }
    }

    @Test
    fun `retry triggers user list refresh`() = runTest {
        fakeUserRepository.emitUsersState(
            DataStateSuccess(emptyList()),
            DataStateSuccess(emptyList()) // emits twice for retry
        )

        viewModel.userListState.test {
            awaitItem() // initial emission
            viewModel.retry()
            awaitItem() // refresh emission
        }
    }

    @Test
    fun `loadMore sets Loading then Idle on success`() = runTest {
        fakeUserRepository.emitLoadMoreUsersState(DataStateSuccess(Unit))

        viewModel.loadMoreState.test {
            assertEquals(LoadMoreUiState.Idle, awaitItem()) // Initial state
            viewModel.loadMore()
            assertEquals(LoadMoreUiState.Loading, awaitItem())
            assertEquals(LoadMoreUiState.Idle, awaitItem())
        }
    }

    @Test
    fun `loadMore sets Loading then Error on failure`() = runTest {
        fakeUserRepository.emitLoadMoreUsersState(DataStateError(Exception("Load more failed")))

        viewModel.loadMoreState.test {
            assertEquals(LoadMoreUiState.Idle, awaitItem())
            viewModel.loadMore()
            assertEquals(LoadMoreUiState.Loading, awaitItem())
            val errorState = awaitItem()
            assertTrue(errorState is LoadMoreUiState.Error)
            assertEquals("Load more failed", errorState.message)
        }
    }
}
