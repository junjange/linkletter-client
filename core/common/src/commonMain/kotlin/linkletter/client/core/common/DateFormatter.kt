package linkletter.client.core.common

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun String.formatRssDateToKorean(): String {
    return try {
        val parts = this.split(" ")
        if (parts.size < 6) return this

        val day = parts[1].padStart(2, '0')
        val month =
            when (parts[2]) {
                "Jan" -> "01"
                "Feb" -> "02"
                "Mar" -> "03"
                "Apr" -> "04"
                "May" -> "05"
                "Jun" -> "06"
                "Jul" -> "07"
                "Aug" -> "08"
                "Sep" -> "09"
                "Oct" -> "10"
                "Nov" -> "11"
                "Dec" -> "12"
                else -> return this
            }
        val year = parts[3]
        val time = parts[4]

        val isoString = "$year-$month-${day}T$time"
        val instant = Instant.parse("$isoString+09:00")
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

        "${localDateTime.year}년 ${localDateTime.monthNumber}월 ${localDateTime.dayOfMonth}일"
    } catch (e: Exception) {
        this
    }
}
