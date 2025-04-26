package io.github.tranhuuluong.kmpgithubclient.user.data.remote

import io.github.tranhuuluong.kmpgithubclient.core.DataStateError
import io.github.tranhuuluong.kmpgithubclient.core.DataStateSuccess
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KtorUserRemoteDataSourceTest {

    private val json = Json { ignoreUnknownKeys = true }

    private fun createHttpClient(mockEngine: MockEngine): HttpClient {
        return HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(json)
            }
        }
    }

    @Test
    fun `getUsers returns DataStateSuccess on 2xx response`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """[{"id":1,"login":"user1"}]""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val dataSource = KtorUserRemoteDataSource(createHttpClient(mockEngine))

        val result = dataSource.getUsers(since = 0, perPage = 10)

        assertTrue(result is DataStateSuccess)
        val users = result.data
        assertEquals(1, users.size)
        assertEquals("user1", users[0].login)
    }

    @Test
    fun `getUsers returns DataStateError on non-2xx response`() = runTest {
        val mockEngine = MockEngine { request ->
            respondBadRequest()
        }
        val dataSource = KtorUserRemoteDataSource(createHttpClient(mockEngine))

        val result = dataSource.getUsers(since = 0, perPage = 10)

        assertTrue(result is DataStateError)
    }

    @Test
    fun `getUserDetail returns DataStateSuccess on 2xx response`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """{"id":1,"login":"user1","node_id":"node1"}""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val dataSource = KtorUserRemoteDataSource(createHttpClient(mockEngine))

        val result = dataSource.getUserDetail(githubId = "user1")

        assertTrue(result is DataStateSuccess)
        val detail = result.data
        assertEquals("user1", detail.login)
    }

    @Test
    fun `getUserDetail returns DataStateError on non-2xx response`() = runTest {
        val mockEngine = MockEngine {
            respondBadRequest()
        }
        val dataSource = KtorUserRemoteDataSource(createHttpClient(mockEngine))

        val result = dataSource.getUserDetail(githubId = "user1")

        assertTrue(result is DataStateError)
    }
}
