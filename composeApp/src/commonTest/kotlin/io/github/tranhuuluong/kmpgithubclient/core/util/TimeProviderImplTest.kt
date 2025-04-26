package io.github.tranhuuluong.kmpgithubclient.core.util

import kotlinx.datetime.TimeZone
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TimeProviderImplTest {

    private lateinit var timeProvider: TimeProviderImpl

    @BeforeTest
    fun setUp() {
        timeProvider = TimeProviderImpl()
    }

    @Test
    fun `systemDefaultTimezone returns the current system timezone`() {
        val expectedTimeZone = TimeZone.currentSystemDefault()
        val actualTimeZone = timeProvider.systemDefaultTimezone()

        assertEquals(expectedTimeZone, actualTimeZone)
    }
}