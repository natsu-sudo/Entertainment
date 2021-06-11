package com.coding.entertainmentlist.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*

@TypeConverters
class DBTypeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}