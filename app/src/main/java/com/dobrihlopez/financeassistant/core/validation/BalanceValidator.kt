package com.dobrihlopez.financeassistant.core.validation

object BalanceValidator {
    private val regex = Regex("^\\d+(\\.\\d{0,2})?$")

    fun validate(value: String): Boolean {
        val formattedValue = value.trim()
        return formattedValue.matches(regex) && formattedValue.isNotBlank()
    }
}