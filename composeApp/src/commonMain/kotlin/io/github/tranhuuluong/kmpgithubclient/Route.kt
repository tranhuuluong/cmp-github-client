package io.github.tranhuuluong.kmpgithubclient

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object UserGraph : Route // route to base navigation graph

    @Serializable
    data object UserListing : Route

    @Serializable
    data class UserDetail(val id: String) : Route
}