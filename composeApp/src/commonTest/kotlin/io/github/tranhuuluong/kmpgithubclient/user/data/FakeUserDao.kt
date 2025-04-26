package io.github.tranhuuluong.kmpgithubclient.user.data

import io.github.tranhuuluong.kmpgithubclient.user.data.local.UserDao
import io.github.tranhuuluong.kmpgithubclient.user.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

internal class FakeUserDao : UserDao {

    private val users = mutableListOf<UserEntity>()
    private val usersFlow = MutableStateFlow<List<UserEntity>>(emptyList())

    override suspend fun upsert(users: List<UserEntity>) {
        users.forEach { upsertInternal(it) }
        usersFlow.value = this.users.toList()
    }

    override suspend fun upsert(user: UserEntity) {
        upsertInternal(user)
        usersFlow.value = users.toList()
    }

    private fun upsertInternal(user: UserEntity) {
        val index = users.indexOfFirst { it.id == user.id }
        if (index != -1) {
            users[index] = user
        } else {
            users.add(user)
        }
    }

    override suspend fun isEmpty(): Boolean {
        return users.isEmpty()
    }

    override suspend fun getLastUsedId(): Long? {
        return users.maxByOrNull { it.id }?.id
    }

    override fun getAllUsers(): Flow<List<UserEntity>> {
        return usersFlow
    }

    override fun getUserByGithubId(githubId: String): Flow<UserEntity?> {
        return usersFlow.map { list -> list.find { it.githubId == githubId } }
    }
}