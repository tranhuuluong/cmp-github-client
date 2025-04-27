package io.github.tranhuuluong.kmpgithubclient.core.util

import kotlinx.datetime.TimeZone

/**
 * Abstraction for providing time-related utilities.
 */
interface TimeProvider {
    /**
     * Returns the system's default time zone.
     *
     * @return The system's [TimeZone].
     */
    fun systemDefaultTimezone(): TimeZone
}

class TimeProviderImpl : TimeProvider {
    override fun systemDefaultTimezone(): TimeZone = TimeZone.currentSystemDefault()
}