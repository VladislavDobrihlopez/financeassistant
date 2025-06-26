package com.dobrihlopez.financeassistant.core

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Serializer for java.time.LocalDate
 * Formats as "yyyy-MM-dd" per ISO_LOCAL_DATE.
 */
object LocalDateSerializer : KSerializer<LocalDate> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDate =
        LocalDate.parse(decoder.decodeString(), formatter)
}

/**
 * Serializer for java.time.LocalTime
 * Formats as "HH:mm:ss.SSS" or shorter per ISO_LOCAL_TIME.
 */
object LocalTimeSerializer : KSerializer<LocalTime> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalTime) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalTime =
        LocalTime.parse(decoder.decodeString(), formatter)
}

/**
 * Serializer for java.time.LocalDateTime
 * Formats as "yyyy-MM-dd'T'HH:mm:ss.SSS" per ISO_LOCAL_DATE_TIME.
 */
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime =
        LocalDateTime.parse(decoder.decodeString(), formatter)
}
