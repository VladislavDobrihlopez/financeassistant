package com.dobrihlopez.financeassistant.feature.transactioncore

import android.accounts.Account
import com.dobrihlopez.financeassistant.core.Transaction
import com.dobrihlopez.financeassistant.feature.categories.domain.Category

fun previewTransactions(): List<Transaction> = listOf(
    Transaction(
        id = 1,
        amount = "100 000 ₽",
        category = Category("🏠", 1, false, "Аренда квартиры"),
        comment = "",
        account = Account("com.bank", "1"),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 2,
        amount = "100 000 ₽",
        category = Category("👗", 2, false, "Одежда"),
        comment = "",
        account = Account("com.bank", "1"),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 3,
        amount = "100 000 ₽",
        category = Category("🐶", 3, false, "На собачку"),
        comment = "Джек",
        account = Account("com.bank", "1"),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 4,
        amount = "100 000 ₽",
        category = Category("🐶", 4, false, "На собачку"),
        comment = "Энни",
        account = Account("com.bank", "1"),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 5,
        amount = "100 000 ₽",
        category = Category("РК", 5, false, "Ремонт квартиры"),
        comment = "",
        account = Account("com.bank", "1"),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 6,
        amount = "100 000 ₽",
        category = Category("🍭", 6, false, "Продукты"),
        comment = "",
        account = Account("com.bank", "1"),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 7,
        amount = "100 000 ₽",
        category = Category("🏋️", 7, false, "Спортзал"),
        comment = "",
        account = Account("com.bank", "1"),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 8,
        amount = "100 000 ₽",
        category = Category("💊", 8, false, "Медицина"),
        comment = "",
        account = Account("com.bank", "1"),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    )
)

fun previewIncomeTransactions(): List<Transaction> = listOf(
    Transaction(
        id = 1,
        amount = "+500 000 ₽",
        category = Category(id = 1, isIncome = true, name = "Зарплата"),
        comment = "",
        account = Account("com.bank", "1"),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 2,
        amount = "+100 000 ₽",
        category = Category( id = 2, isIncome = true, name = "Подработка"),
        comment = "",
        account = Account("com.bank", "1"),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    )
)
