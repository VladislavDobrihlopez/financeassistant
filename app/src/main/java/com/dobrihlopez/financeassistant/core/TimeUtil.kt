package com.dobrihlopez.financeassistant.core

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun LocalDate.atEndOfDay(zoneId: ZoneId = ZoneId.systemDefault()): ZonedDateTime {
    return this.atTime(LocalTime.MAX).atZone(zoneId)
}

private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")
private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

fun String.toFullDateAndTimeFormat() = OffsetDateTime.parse(this).format(formatter)

fun String.toLocalDate() = OffsetDateTime.parse(this).toLocalDate()

fun String.toLocalTime() = OffsetDateTime.parse(this).toLocalTime()

fun String.toTimeFormat(): String = OffsetDateTime.parse(this).format(timeFormatter)

fun String.toDateFormat(): String = OffsetDateTime.parse(this).format(dateFormatter)

fun LocalDateTime.toTimeFormat(): String = format(timeFormatter)

fun LocalDateTime.toDateFormat(): String = format(dateFormatter)

fun LocalTime.toTimeFormat(): String = format(timeFormatter)

fun LocalDate.toDateFormat(): String = format(dateFormatter)
