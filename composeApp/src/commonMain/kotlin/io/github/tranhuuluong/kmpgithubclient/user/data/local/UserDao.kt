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

    @Query("SELECT (SELECT COUNT(*) FROM ${UserEntity.TABLE_NAME}) == 0")
    suspend fun isEmpty(): Boolean

    @Query("SELECT MAX(${UserEntity.COLUMN_ID}) FROM ${UserEntity.TABLE_NAME}")
    suspend fun getLastUsedId(): Long?

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME}")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.COLUMN_GITHUB_ID} = :githubId")
    fun getUserByGithubId(githubId: String): Flow<UserEntity?>
}