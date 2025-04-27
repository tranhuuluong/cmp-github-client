package io.github.tranhuuluong.kmpgithubclient.design_system.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Displays the app's top app bar with a title and optional navigation icon.
 *
 * @param title The title text displayed in the app bar.
 * @param showNavigationIcon Whether to show the navigation (back) icon.
 * @param onNavigationClick Callback invoked when the navigation icon is clicked.
 * @param scrollBehavior Optional scroll behavior for the app bar.
 * @param modifier Modifier to apply to the app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GhcAppBar(
    title: String,
    showNavigationIcon: Boolean,
    onNavigationClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    modifier: Modifier = Modifier,
) {
    MediumTopAppBar(
        modifier = modifier,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            if (showNavigationIcon) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
        ),
        scrollBehavior = scrollBehavior
    )
}