package io.github.tranhuuluong.kmpgithubclient.user.data.local

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object GhcDatabaseConstructor : RoomDatabaseConstructor<GhcDatabase> {
    override fun initialize(): GhcDatabase
}