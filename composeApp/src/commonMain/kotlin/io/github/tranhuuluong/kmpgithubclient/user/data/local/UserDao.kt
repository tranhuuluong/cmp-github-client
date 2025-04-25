package io.github.tranhuuluong.kmpgithubclient.user.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.github.tranhuuluong.kmpgithubclient.user.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Upsert
    suspend fun upsert(users: List<UserEntity>)

    @Upsert
    suspend fun upsert(user: UserEntity)

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME}")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE id = :id")
    fun getUser(id: String): Flow<UserEntity?>

    @Query("SELECT (SELECT COUNT(*) FROM ${UserEntity.TABLE_NAME}) == 0")
    suspend fun isEmpty(): Boolean
}