package com.dobrihlopez.financeassistant.feature

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsComponent
import com.dobrihlopez.financeassistant.feature.categories.presentation.CategoriesComponent
import com.dobrihlopez.financeassistant.feature.settings.presentation.SettingsComponent
import com.dobrihlopez.financeassistant.feature.transaction_core.expenses.presentation.ExpenseComponent
import com.dobrihlopez.financeassistant.feature.transaction_core.income.presentation.IncomeComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable

interface RootComponent {
    val state: Value<ChildStack<*, Child>>

    fun onExpensesClick()
    fun onIncomeClick()
    fun onAccountsClick()
    fun onCategoriesClick()
    fun onSettingsClick()

    sealed interface Child {
        data class Accounts(val component: AccountsComponent) : Child
        data class Category(val component: CategoriesComponent) : Child
        data class Expenses(val component: ExpenseComponent) : Child
        data class Income(val component: IncomeComponent) : Child
        data class Settings(val component: SettingsComponent) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Accounts : Config
        @Serializable
        data object Categories : Config
        @Serializable
        data object Expenses : Config
        @Serializable
        data object Income : Config
        @Serializable
        data object Settings : Config
    }

    class DefaultRootComponent @AssistedInject constructor(
        private val expenseComponentFactory: ExpenseComponent.Factory,
        private val incomeComponentFactory: IncomeComponent.Factory,
        private val categoriesComponentFactory: CategoriesComponent.Factory,
        private val accountsComponentFactory: AccountsComponent.Factory,
        private val settingsComponentFactory: SettingsComponent.Factory,
        @Assisted("componentContext") private val componentContext: ComponentContext,
    ) : RootComponent, ComponentContext by componentContext {

        private val stack = StackNavigation<Config>()

        override val state: Value<ChildStack<Config, Child>> = childStack(
                key = "root_stack",
                source = stack,
                initialConfiguration = Config.Expenses,
                handleBackButton = true,
                childFactory = ::child,
                serializer = Config.serializer()
            )

        private fun child(config: Config, componentContext: ComponentContext): Child {
            return when (config) {
                Config.Accounts -> Child.Accounts(accountsComponentFactory.create(componentContext))
                Config.Categories -> Child.Category(categoriesComponentFactory.create(componentContext))
                Config.Expenses -> Child.Expenses(expenseComponentFactory.create(componentContext))
                Config.Income -> Child.Income(incomeComponentFactory.create(componentContext))
                Config.Settings -> Child.Settings(settingsComponentFactory.create(componentContext))
            }
        }

        override fun onExpensesClick() {
            stack.bringToFront(Config.Expenses)
        }

        override fun onIncomeClick() {
            stack.bringToFront(Config.Income)
        }

        override fun onAccountsClick() {
            stack.bringToFront(Config.Accounts)
        }

        override fun onCategoriesClick() {
            stack.bringToFront(Config.Categories)
        }

        override fun onSettingsClick() {
            stack.bringToFront(Config.Settings)
        }

        @AssistedFactory
        interface Factory {
            fun create(@Assisted("componentContext") componentContext: ComponentContext): DefaultRootComponent
        }
    }
}
