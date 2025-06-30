package com.dobrihlopez.financeassistant.core.validation

/**
 * Валидатор корректности введённого значения баланса.
 *
 * Выполняет проверку строки на соответствие следующим условиям:
 * - Содержит только цифры и, при необходимости, десятичную точку;
 * - После точки — не более двух цифр;
 * - Строка не должна быть пустой или состоять только из пробелов.
 *
 * Примеры допустимых значений: `"100"`, `"100.5"`, `"100.55"`
 * Примеры недопустимых значений: `"-100."`, `"100.555"`, `"abc"`, `""`
 */
object BalanceValidator {
    private val regex = Regex("^\\d+(\\.\\d{0,2})?$")

    fun validate(value: String): Boolean {
        val formattedValue = value.trim()
        return formattedValue.matches(regex) && formattedValue.isNotBlank()
    }
}
