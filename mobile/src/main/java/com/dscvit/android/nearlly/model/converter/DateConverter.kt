package com.dscvit.android.nearlly.model.converter

import android.arch.persistence.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long) = Date(timestamp)

    @TypeConverter
    fun toTimeStamp(date: Date) = date.time
}