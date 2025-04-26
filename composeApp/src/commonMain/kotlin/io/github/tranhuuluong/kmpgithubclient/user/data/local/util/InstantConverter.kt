package io.github.tranhuuluong.kmpgithubclient.user.data.local.util

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

internal object InstantConverter {

    @TypeConverter
    fun fromInstant(instant: Instant?): String? {
        return instant?.toString()
    }

    @TypeConverter
    fun toInstant(isoString: String?): Instant? {
        return isoString?.let { Instant.parse(it) }
    }
}