package com.oscargil80.codingmeetroomnoteapp.converters

import java.util.Date
import androidx.room.TypeConverter

class TypeConverter {

    @TypeConverter
    fun fromTimestamp(value:Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date:Date): Long {
        return date.time
    }

}