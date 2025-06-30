package com.dobrihlopez.financeassistant.core_remote.network

import kotlinx.coroutines.delay
import okio.IOException
import retrofit2.HttpException
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Повторяет выполнение запросов в нескольких попытках.
 *
 * Используется для повторных попыток в случае сетевых ошибок (например, `IOException`)
 * или ошибки сервера (HttpException с code error 500).
 *
 * @param interval Интервал между попытками (по умолчанию — 2 секунды).
 * В продакшене желательно использовать exponential backoff.
 *
 * @param attempts Общее количество попыток (включая последнюю, финальную). По умолчанию — 3.
 * @param block Блок кода, который требуется выполнить. Может быть приостановлен.
 *
 * @return Результат выполнения блока `block`, если одна из попыток завершилась успешно.
 * @throws Throwable Если после всех попыток блок завершился неудачно, будет выброшено последнее исключение.
 */
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
        } catch (_: IOException) {
        }

        delay(interval) // better to use exponential backoff in production: interval * pow(2, currentAttempt)
    }

    return block()
}
