package io.github.tranhuuluong.kmpgithubclient.user.data.local

import androidx.room.RoomDatabase

expect class DatabaseBuilderFactory {
    fun create(): RoomDatabase.Builder<GhcDatabase>
}