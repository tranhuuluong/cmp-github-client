package io.github.tranhuuluong.kmpgithubclient.core.util

import kotlinx.datetime.TimeZone

interface TimeProvider {
    fun systemDefaultTimezone(): TimeZone
}

class TimeProviderImpl : TimeProvider {
    override fun systemDefaultTimezone(): TimeZone = TimeZone.currentSystemDefault()
}