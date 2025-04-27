package io.github.tranhuuluong.kmpgithubclient

import GhcTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import io.github.tranhuuluong.kmpgithubclient.design_system.component.GhcAppBar
import io.github.tranhuuluong.kmpgithubclient.user.presentation.navigation.Route
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail.UserDetailRoute
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.UserRoute
import kmpgithubclient.composeapp.generated.resources.Res
import kmpgithubclient.composeapp.generated.resources.default_app_bar_title
import kmpgithubclient.composeapp.generated.resources.user_detail_title
import kmpgithubclient.composeapp.generated.resources.user_list_title
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    GhcTheme {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val appBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(appBarScrollBehavior.nestedScrollConnection),
                topBar = {
                    GhcAppBar(
                        title = stringResource(currentBackStackEntry.getTopBarTitle()),
                        showNavigationIcon = navController.canNavigateUp(),
                        scrollBehavior = appBarScrollBehavior,
                        onNavigationClick = { navController.navigateUp() },
                    )
                }
            ) {
                NavHost(
                    modifier = Modifier.padding(it),
                    navController = navController,
                    startDestination = Route.UserGraph,
                ) {
                    navigation<Route.UserGraph>(
                        startDestination = Route.UserListing
                    ) {
                        composable<Route.UserListing> {
                            UserRoute(
                                onUserClick = { user ->
                                    navController.navigate(Route.UserDetail(id = user.id))
                                }
                            )
                        }

                        composable<Route.UserDetail> {
                            UserDetailRoute()
                        }
                    }
                }
            }
        }
    }
}

/**
 * Returns the top bar title based on the current navigation destination.
 */
private fun NavBackStackEntry?.getTopBarTitle(): StringResource {
    val destination = this?.destination
    return when {
        destination == null -> Res.string.default_app_bar_title
        destination.hasRoute<Route.UserListing>() -> Res.string.user_list_title
        destination.hasRoute<Route.UserDetail>() -> Res.string.user_detail_title
        else -> Res.string.default_app_bar_title
    }
}

/**
 * Can navigate up if there is a previous back stack entry.
 * (Good enough for simple graphs; nested graphs may need smarter handling.)
 */
private fun NavController.canNavigateUp(): Boolean {
    return previousBackStackEntry != null
}