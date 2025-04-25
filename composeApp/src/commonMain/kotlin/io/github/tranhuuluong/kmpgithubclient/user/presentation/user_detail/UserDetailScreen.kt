package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun UserDetailRoute(
    userId: String,
    modifier: Modifier = Modifier,
) {
    UserDetailScreen(
        userId = userId,
        modifier = modifier
    )
}

@Composable
internal fun UserDetailScreen(
    userId: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "User ID: $userId")
    }
}