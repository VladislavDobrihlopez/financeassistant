package com.dobrihlopez.financeassistant.core

fun String.currencyToSymbol(): Char {
    return when (this) {
        "USD" -> '$'
        "EUR" -> '€'
        "RUB" -> '₽'
        else -> throw IllegalArgumentException("$this not supported. Only [USD, EUR, RUB]")
    }
}
