package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.github.tranhuuluong.kmpgithubclient.design_system.component.ErrorView
import io.github.tranhuuluong.kmpgithubclient.design_system.utils.reachedBottom
import kmpgithubclient.composeapp.generated.resources.Res
import kmpgithubclient.composeapp.generated.resources.retry
import kmpgithubclient.composeapp.generated.resources.some_thing_went_wrong
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun UserRoute(
    onUserClick: (UserUiModel) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserListViewModel = koinViewModel()
) {
    val userListState by viewModel.userListState.collectAsStateWithLifecycle()
    val loadMoreState by viewModel.loadMoreState.collectAsStateWithLifecycle()
    UserListScreen(
        state = userListState,
        loadMoreState = loadMoreState,
        onUserClick = onUserClick,
        onLoadMore = viewModel::loadMore,
        onRetryClick = viewModel::retry,
        modifier = modifier
    )
}

@Composable
internal fun UserListScreen(
    state: UserListUiState,
    loadMoreState: LoadMoreUiState,
    onUserClick: (UserUiModel) -> Unit,
    onLoadMore: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            is UserListUiState.Loading -> CircularProgressIndicator()
            is UserListUiState.Success -> UserList(
                users = state.users,
                loadMoreState = loadMoreState,
                onUserClick = onUserClick,
                onLoadMore = onLoadMore
            )

            is UserListUiState.Error -> ErrorView(
                modifier = Modifier.align(Alignment.TopCenter),
                onRetry = onRetryClick,
            )
            // TODO
            is UserListUiState.Empty -> {}
        }
    }
}

@Composable
private fun UserList(
    users: List<UserUiModel>,
    loadMoreState: LoadMoreUiState,
    onUserClick: (UserUiModel) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val reachedBottom by remember {
        derivedStateOf { lazyListState.reachedBottom() }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            onLoadMore()
        }
    }
    LazyColumn(
        state = lazyListState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = users,
            key = { user -> user.id }
        ) { user ->
            User(user, onUserClick)
        }

        when (loadMoreState) {
            is LoadMoreUiState.Loading -> item {
                CircularProgressIndicator()
            }

            is LoadMoreUiState.Error -> item {
                RetryTextButton(onClick = onLoadMore)
            }

            is LoadMoreUiState.Idle -> {}
        }
    }
}

@Composable
private fun User(
    user: UserUiModel,
    onUserClick: (UserUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),
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
                    text = user.id,
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

@Composable
private fun RetryTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {
                append(stringResource(Res.string.some_thing_went_wrong))
                appendLine()
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                    )
                ) {
                    append(stringResource(Res.string.retry))
                }
            },
            color = MaterialTheme.colorScheme.error,
        )
    }
}