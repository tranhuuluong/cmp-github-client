package io.github.tranhuuluong.kmpgithubclient.user.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.tranhuuluong.kmpgithubclient.user.data.local.entity.UserEntity
import io.github.tranhuuluong.kmpgithubclient.user.data.local.util.InstantConverter

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1
)
@ConstructedBy(GhcDatabaseConstructor::class)
@TypeConverters(
    InstantConverter::class,
)
abstract class GhcDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "ghc-database"
    }
}