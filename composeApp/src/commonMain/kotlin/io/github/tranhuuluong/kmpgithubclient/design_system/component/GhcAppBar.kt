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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import io.github.tranhuuluong.kmpgithubclient.user.presentation.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GhcAppBar(
    navBackStackEntry: NavBackStackEntry?,
    onBackButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    modifier: Modifier = Modifier,
) {
    MediumTopAppBar(
        modifier = modifier,
        title = {
            Text(text = navBackStackEntry.getTopBarTitle())
        },
        navigationIcon = {
            if (navBackStackEntry?.destination?.hasRoute<Route.UserListing>() != true) {
                IconButton(onClick = onBackButtonClick) {
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

private fun NavBackStackEntry?.getTopBarTitle(): String {
    val destination = this?.destination
    return when {
        destination == null -> ""
        destination.hasRoute<Route.UserListing>() -> "Github Users"
        destination.hasRoute<Route.UserDetail>() -> "User Detail"
        else -> ""
    }
}