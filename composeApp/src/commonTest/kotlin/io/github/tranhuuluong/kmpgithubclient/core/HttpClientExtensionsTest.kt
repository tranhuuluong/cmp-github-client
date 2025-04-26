package io.github.tranhuuluong.kmpgithubclient.core

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class HttpClientExtensionsTest {

    @Serializable
    private data class SampleResponse(val message: String)

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `safeCall returns DataStateSuccess on 2xx response`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """{"message": "Hello"}""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(json)
            }
        }

        val result = safeCall<SampleResponse> {
            client.get("http://test.com")
        }

        assertTrue(result is DataStateSuccess)
        assertEquals("Hello", result.data.message)
    }

    @Test
    fun `safeCall returns DataStateError on non-2xx response`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """{"error": "Bad Request"}""",
                status = HttpStatusCode.BadRequest
            )
        }
        val client = HttpClient(mockEngine)

        val result = safeCall<SampleResponse> {
            client.get("http://test.com")
        }

        assertTrue(result is DataStateError)
        assertEquals("Something went wrong!", result.exception.message)
    }

    @Test
    fun `safeCall catches exception and returns DataStateError`() = runTest {
        val result = safeCall<SampleResponse> {
            throw RuntimeException("Network failure")
        }

        assertTrue(result is DataStateError)
        assertEquals("Network failure", result.exception.message)
    }

    @Test
    fun `safeCall rethrows CancellationException`() = runTest {
        val job = launch {
            coroutineContext.cancel()

            assertFailsWith<CancellationException> {
                safeCall<SampleResponse> {
                    throw Exception("Something went wrong!")
                }
            }
        }
        job.join()
    }
}
