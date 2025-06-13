package com.dobrihlopez.financeassistant.feature.transaction_core.core

import com.dobrihlopez.financeassistant.core.Transaction
import com.dobrihlopez.financeassistant.feature.accounts.domain.UserAccountDetailed
import com.dobrihlopez.financeassistant.feature.categories.domain.Category

fun previewTransactions(): List<Transaction> = listOf(
    Transaction(
        id = 1,
        amount = "100 000 ₽",
        category = Category("🏠", 1, false, "Аренда квартиры"),
        comment = "",
        account = UserAccountDetailed(
            id = 1,
            name = "Мой счёт",
            balance = "-670 000",
            currency = "₽",
            createdAt = "",
            updatedAt = "",
            expenseStats = emptyList(),
            incomeStatistics = emptyList()
        ),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 2,
        amount = "100 000 ₽",
        category = Category("👗", 2, false, "Одежда"),
        comment = "",
        account = UserAccountDetailed(
            id = 1,
            name = "Мой счёт",
            balance = "-670 000",
            currency = "₽",
            createdAt = "",
            updatedAt = "",
            expenseStats = emptyList(),
            incomeStatistics = emptyList()
        ),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 3,
        amount = "100 000 ₽",
        category = Category("🐶", 3, false, "На собачку"),
        comment = "Джек",
        account = UserAccountDetailed(
            id = 1,
            name = "Мой счёт",
            balance = "-670 000",
            currency = "₽",
            createdAt = "",
            updatedAt = "",
            expenseStats = emptyList(),
            incomeStatistics = emptyList()
        ),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 4,
        amount = "100 000 ₽",
        category = Category("🐶", 4, false, "На собачку"),
        comment = "Энни",
        account = UserAccountDetailed(
            id = 1,
            name = "Мой счёт",
            balance = "-670 000",
            currency = "₽",
            createdAt = "",
            updatedAt = "",
            expenseStats = emptyList(),
            incomeStatistics = emptyList()
        ),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 5,
        amount = "100 000 ₽",
        category = Category("рк", 5, false, "Ремонт квартиры"),
        comment = "",
        account = UserAccountDetailed(
            id = 1,
            name = "Мой счёт",
            balance = "-670 000",
            currency = "₽",
            createdAt = "",
            updatedAt = "",
            expenseStats = emptyList(),
            incomeStatistics = emptyList()
        ),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 6,
        amount = "100 000 ₽",
        category = Category("🍭", 6, false, "Продукты"),
        comment = "",
        account = UserAccountDetailed(
            id = 1,
            name = "Мой счёт",
            balance = "-670 000",
            currency = "₽",
            createdAt = "",
            updatedAt = "",
            expenseStats = emptyList(),
            incomeStatistics = emptyList()
        ),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 7,
        amount = "100 000 ₽",
        category = Category("🏋️", 7, false, "Спортзал"),
        comment = "",
        account = UserAccountDetailed(
            id = 1,
            name = "Мой счёт",
            balance = "-670 000",
            currency = "₽",
            createdAt = "",
            updatedAt = "",
            expenseStats = emptyList(),
            incomeStatistics = emptyList()
        ),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 8,
        amount = "100 000 ₽",
        category = Category("💊", 8, false, "Медицина"),
        comment = "",
        account = UserAccountDetailed(
            id = 1,
            name = "Мой счёт",
            balance = "-670 000",
            currency = "₽",
            createdAt = "",
            updatedAt = "",
            expenseStats = emptyList(),
            incomeStatistics = emptyList()
        ),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 500,
        amount = "100 000 ₽",
        category = Category("рк", 5, false, "Ремонт квартиры"),
        comment = "",
        account = UserAccountDetailed(
            id = 1,
            name = "Мой счёт",
            balance = "-670 000",
            currency = "₽",
            createdAt = "",
            updatedAt = "",
            expenseStats = emptyList(),
            incomeStatistics = emptyList()
        ),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 100,
        amount = "100 000 ₽",
        category = Category("🍭", 6, false, "Продукты"),
        comment = "",
        account = UserAccountDetailed(
            id = 1,
            name = "Мой счёт",
            balance = "-670 000",
            currency = "₽",
            createdAt = "",
            updatedAt = "",
            expenseStats = emptyList(),
            incomeStatistics = emptyList()
        ),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
)

fun previewIncomeTransactions(): List<Transaction> = listOf(
    Transaction(
        id = 1,
        amount = "500 000 ₽",
        category = Category(null, 1, true, "Зарплата"),
        comment = "",
        account = UserAccountDetailed(
            id = 1,
            name = "Мой счёт",
            balance = "-670 000",
            currency = "₽",
            createdAt = "",
            updatedAt = "",
            expenseStats = emptyList(),
            incomeStatistics = emptyList()
        ),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
    Transaction(
        id = 2,
        amount = "200 000 ₽",
        category = Category(null, 2, true, "Подработка"),
        comment = "",
        account = UserAccountDetailed(
            id = 1,
            name = "Мой счёт",
            balance = "-670 000",
            currency = "₽",
            createdAt = "",
            updatedAt = "",
            expenseStats = emptyList(),
            incomeStatistics = emptyList()
        ),
        createdAt = "",
        updatedAt = "",
        transactionDate = "2025-06-08"
    ),
)
