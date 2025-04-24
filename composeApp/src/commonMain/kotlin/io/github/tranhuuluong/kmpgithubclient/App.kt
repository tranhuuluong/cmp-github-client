package io.github.tranhuuluong.kmpgithubclient

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_detail.UserDetailRoute
import io.github.tranhuuluong.kmpgithubclient.user.presentation.user_list.UserRoute
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.UserGraph,
        ) {
            navigation<Route.UserGraph>(
                startDestination = Route.UserListing
            ) {
                composable<Route.UserListing> {
                    UserRoute(
                        onUserClick = {
                            navController.navigate(Route.UserDetail(id = "userId"))
                        }
                    )
                }

                composable<Route.UserDetail> { navBackStackEntry ->
                    val args = navBackStackEntry.toRoute<Route.UserDetail>()
                    UserDetailRoute(
                        userId = args.id,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}