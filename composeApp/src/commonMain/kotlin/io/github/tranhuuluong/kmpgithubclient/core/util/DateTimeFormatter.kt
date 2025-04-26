package io.github.tranhuuluong.kmpgithubclient.core.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

object DateTimeFormatter {

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