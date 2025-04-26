package io.github.tranhuuluong.kmpgithubclient.user.data.local.util

import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNull

class InstantConverterTest {

    @Test
    fun `fromInstant returns ISO string when Instant is not null`() {
        val instant = Instant.parse("2023-04-26T10:15:30Z")
        val result = InstantConverter.fromInstant(instant)
        assertEquals("2023-04-26T10:15:30Z", result)
    }

    @Test
    fun `fromInstant returns null when Instant is null`() {
        val result = InstantConverter.fromInstant(null)
        assertNull(result)
    }

    @Test
    fun `toInstant returns Instant when ISO string is valid`() {
        val isoString = "2023-04-26T10:15:30Z"
        val result = InstantConverter.toInstant(isoString)
        assertEquals(Instant.parse(isoString), result)
    }

    @Test
    fun `toInstant returns null when ISO string is null`() {
        val result = InstantConverter.toInstant(null)
        assertNull(result)
    }

    @Test
    fun `toInstant throws exception when ISO string is invalid`() {
        val invalidIsoString = "invalid-date"
        assertFails {
            InstantConverter.toInstant(invalidIsoString)
        }
    }
}