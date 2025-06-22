package com.dobrihlopez.financeassistant.core.network

import kotlinx.coroutines.delay
import okio.IOException
import retrofit2.HttpException
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

suspend fun <T> retryWithDelay(
    interval: Duration = 2.seconds,
    attempts: Int = 3,
    block: suspend () -> T,
): T {
    repeat(attempts - 1) {
        try {
            return block()
        } catch (e: HttpException) {
            if (e.code() != 500) throw e
        } catch (e: IOException) {

        }

        delay(interval) // better to use exponential backoff in production: interval * pow(2, currentAttempt)
    }

    return block()
}