package com.vhontar.bookify.aaa.db.converter

import androidx.room.TypeConverter
import org.joda.time.DateTime

/**
 * Created by Vladyslav Hontar (vhontar) on 01.02.21.
 *
 * Type converters to allow Room to reference complex data types.
 */
class DateTimeConverter {
    @TypeConverter fun dateTimeToMillis(dateTime: DateTime): Long = dateTime.millis
    @TypeConverter fun millisToDateTime(millis: Long): DateTime = DateTime(millis)
}