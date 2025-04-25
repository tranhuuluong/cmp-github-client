package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun UserDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel = koinViewModel()
) {
    val userDetailUiState by viewModel.userDetailUiState.collectAsStateWithLifecycle()
    UserDetailScreen(
        state = userDetailUiState,
        modifier = modifier
    )
}

@Composable
internal fun UserDetailScreen(
    state: UserDetailUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            is UserDetailUiState.Loading -> CircularProgressIndicator()
            is UserDetailUiState.Success -> Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(state.avatarUrl)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
                Text(
                    text = state.userName,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                    text = state.id,
                    style = MaterialTheme.typography.bodyLarge
                )
                ProfileStats(
                    followers = state.followers,
                    following = state.following,
                    publicGists = state.publicGists,
                    publicRepos = state.publicRepos,
                    modifier = Modifier.fillMaxWidth(),
                )
                InfoCard(
                    company = state.company,
                    location = state.location,
                    email = state.email,
                    joinDate = state.joinDate,
                    modifier = Modifier.fillMaxWidth(),
                )
                val blog = state.blog
                if (blog.isNotBlank()) {
                    Blog(
                        modifier = Modifier.align(Alignment.Start),
                        blog = blog,
                    )
                }
            }

            else -> {}
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileStats(
    followers: Int,
    following: Int,
    publicGists: Int,
    publicRepos: Int,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = 2,
    ) {
        ProfileStatItem(
            number = followers,
            label = "Followers",
            modifier = Modifier.weight(1f)
        )
        ProfileStatItem(
            number = following,
            label = "Following",
            modifier = Modifier.weight(1f)
        )
        ProfileStatItem(
            number = publicGists,
            label = "Public Gist",
            modifier = Modifier.weight(1f)
        )
        ProfileStatItem(
            number = publicRepos,
            label = "Public Repos",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ProfileStatItem(
    number: Int,
    label: String,
    modifier: Modifier = Modifier,
    numberStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    labelStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    OutlinedCard(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 12.dp,
                ),
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {
                withStyle(numberStyle.copy(fontWeight = FontWeight.Bold).toSpanStyle()) {
                    append("$number")
                }
                appendLine()
                withStyle(labelStyle.toSpanStyle()) {
                    append(label)
                }
            },
            lineHeight = 30.sp,
        )
    }
}

@Composable
private fun InfoCard(
    company: String,
    location: String,
    email: String,
    joinDate: String,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (company.isNotBlank()) {
                InfoItem(icon = Icons.Outlined.Home, text = company)
            }
            if (location.isNotBlank()) {
                InfoItem(icon = Icons.Outlined.LocationOn, text = location)
            }
            if (email.isNotBlank()) {
                InfoItem(icon = Icons.Outlined.Email, text = email)
            }
            if (joinDate.isNotBlank()) {
                InfoItem(icon = Icons.Outlined.DateRange, text = joinDate)
            }
        }
    }
}

@Composable
private fun InfoItem(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Text(text)
    }
}

@Composable
private fun Blog(
    blog: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "Blog",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
            )
        )
        Text(text = blog)
    }
}