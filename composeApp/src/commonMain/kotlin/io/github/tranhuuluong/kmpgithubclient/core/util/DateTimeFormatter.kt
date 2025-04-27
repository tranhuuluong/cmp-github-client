package io.github.tranhuuluong.kmpgithubclient.core.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

/**
 * Utility object for formatting date and time values.
 */
object DateTimeFormatter {

    /**
     * Formats an [Instant] to a local date string in `yyyy-MM-dd` format.
     *
     * @param instant The [Instant] to format.
     * @param timeZone The [TimeZone] to apply for the local date.
     * @return A formatted date string.
     */
    fun formatToLocalDate(
        instant: Instant,
        timeZone: TimeZone
    ): String = instant.toLocalDateTime(timeZone).format(
        LocalDateTime.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            dayOfMonth()
        }
    )
}