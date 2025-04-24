package io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun UserRoute(
    onUserClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    UserListScreen(
        onUserClick = onUserClick,
        modifier = modifier
    )
}

@Composable
internal fun UserListScreen(
    onUserClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onUserClick) {
            Text("Open user detail")
        }
    }
}