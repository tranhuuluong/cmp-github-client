package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade

private val users = List(20) {
    val userId = "user$it"
    UserUiModel(
        id = userId,
        name = "User $it",
        profileUrl = "https://github.com/$userId",
        avatarUrl = "https://avatars.githubusercontent.com/u/$it",
    )
}

@Composable
internal fun UserRoute(
    onUserClick: (UserUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val userListState = UserListUiState.Success(users)
    UserListScreen(
        state = userListState,
        onUserClick = onUserClick,
        modifier = modifier
    )
}

@Composable
internal fun UserListScreen(
    state: UserListUiState,
    onUserClick: (UserUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        is UserListUiState.Loading -> {}
        is UserListUiState.Success -> {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = state.users,
                    key = { user -> user.id }
                ) { user ->
                    User(user, onUserClick)
                }
            }
        }
    }
}

@Composable
private fun User(
    user: UserUiModel,
    onUserClick: (UserUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { onUserClick(user) }
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .height(IntrinsicSize.Max),
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(user.avatarUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = 12.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                Text(
                    text = user.profileUrl,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}