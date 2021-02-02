package com.vhontar.bookify.aaa.db.converter

import androidx.room.TypeConverter

/**
 * Created by Vladyslav Hontar (vhontar) on 02.02.21.
 */
class ArrayListConverter {
    @TypeConverter fun arrayListToString(strings: List<String>): String {
        val finalString = ""
        strings.forEach { "$finalString$ARRAY_LIST_SEPARATOR$it" }
        return finalString
    }
    @TypeConverter fun stringToArrayList(finalString: String): List<String> {
        return finalString.split(ARRAY_LIST_SEPARATOR)
    }

    companion object {
        private const val ARRAY_LIST_SEPARATOR = ";;;"
    }
}