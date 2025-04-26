import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.github.tranhuuluong.kmpgithubclient.core.Result
import io.github.tranhuuluong.kmpgithubclient.core.StateIdle
import io.github.tranhuuluong.kmpgithubclient.core.StateLoading
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.User
import io.github.tranhuuluong.kmpgithubclient.user.domain.model.UserType
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.UserListUiState
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.toUiState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserListUiMapperTest {

    @Test
    fun `toUiState maps StateLoading to Loading`() {
        val result = StateLoading
        val uiState = result.toUiState()

        assertTrue(uiState is UserListUiState.Loading)
    }

    @Test
    fun `toUiState maps DataStateError to Error with message`() {
        val errorMessage = "Something went wrong"
        val result = DataStateError(Exception(errorMessage))
        val uiState = result.toUiState()

        assertTrue(uiState is UserListUiState.Error)
        assertEquals(errorMessage, uiState.message)
    }

    @Test
    fun `toUiState maps DataStateSuccess to Success with UserUiModels`() {
        val users = listOf(
            User(
                id = "user1",
                avatarUrl = "avatar1",
                profileUrl = "profile1",
                type = UserType.User
            ),
            User(
                id = "user2",
                avatarUrl = "avatar2",
                profileUrl = "profile2",
                type = UserType.Organization
            )
        )
        val result: Result<List<User>> = DataStateSuccess(users)
        val uiState = result.toUiState()

        assertTrue(uiState is UserListUiState.Success)

        assertEquals(2, uiState.users.size)
        assertEquals("user1", uiState.users[0].id)
        assertEquals("avatar1", uiState.users[0].avatarUrl)
        assertEquals("profile1", uiState.users[0].profileUrl)

        assertEquals("user2", uiState.users[1].id)
    }

    @Test
    fun `toUiState maps unknown state to Empty`() {
        val unknownState = StateIdle
        val uiState = unknownState.toUiState()

        assertTrue(uiState is UserListUiState.Empty)
    }
}
