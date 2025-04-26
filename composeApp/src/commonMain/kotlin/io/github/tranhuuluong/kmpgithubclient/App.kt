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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import io.github.tranhuuluong.kmpgithubclient.design_system.component.GhcAppBar
import io.github.tranhuuluong.kmpgithubclient.user.presentation.navigation.Route
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail.UserDetailRoute
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.UserRoute
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
                        navBackStackEntry = currentBackStackEntry,
                        scrollBehavior = appBarScrollBehavior,
                        onBackButtonClick = { navController.navigateUp() },
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