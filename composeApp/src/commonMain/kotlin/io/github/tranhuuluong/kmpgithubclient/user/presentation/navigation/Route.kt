package io.github.tranhuuluong.kmpgithubclient.user.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    /**
     * Base route for user-related navigation graphs.
     */
    @Serializable
    data object UserGraph : Route

    /**
     * Route to the User Listing screen.
     */
    @Serializable
    data object UserListing : Route

    /**
     * Route to the User Detail screen.
     *
     * @param id is the github id of the user.
     */
    @Serializable
    data class UserDetail(val id: String) : Route
}