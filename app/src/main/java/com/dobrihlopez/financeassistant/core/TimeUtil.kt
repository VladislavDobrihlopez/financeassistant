package com.dobrihlopez.financeassistant.core

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

fun LocalDate.atEndOfDay(zoneId: ZoneId = ZoneId.systemDefault()): ZonedDateTime {
    return this.atTime(LocalTime.MAX).atZone(zoneId)
}