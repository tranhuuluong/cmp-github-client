package io.github.tranhuuluong.kmpgithubclient.user.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseBuilderFactory(
    private val context: Context,
) {
    actual fun create(): RoomDatabase.Builder<GhcDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(GhcDatabase.DATABASE_NAME)
        return Room.databaseBuilder<GhcDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}