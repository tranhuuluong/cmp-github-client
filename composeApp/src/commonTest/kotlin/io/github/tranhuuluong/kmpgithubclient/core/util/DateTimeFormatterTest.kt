package io.github.tranhuuluong.kmpgithubclient.core.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals

class DateTimeFormatterTest {

    @Test
    fun `formatToLocalDate formats Instant correctly in UTC`() {
        val instant = Instant.parse("2025-04-26T15:30:00Z")
        val timeZone = TimeZone.UTC
        val formattedDate = DateTimeFormatter.formatToLocalDate(instant, timeZone)

        assertEquals("2025-04-26", formattedDate)
    }

    @Test
    fun `formatToLocalDate formats Instant correctly in different timezone`() {
        val instant = Instant.parse("2025-04-26T23:30:00Z") // this time crosses date in +1
        val timeZone = TimeZone.of("UTC+1")
        val formattedDate = DateTimeFormatter.formatToLocalDate(instant, timeZone)

        // In Berlin, this instant is "2025-04-27" due to timezone shift
        assertEquals("2025-04-27", formattedDate)
    }
}