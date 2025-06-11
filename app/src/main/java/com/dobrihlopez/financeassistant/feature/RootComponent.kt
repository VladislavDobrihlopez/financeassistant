package com.dobrihlopez.financeassistant.feature

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsComponent
import com.dobrihlopez.financeassistant.feature.categories.presentation.CategoriesComponent
import com.dobrihlopez.financeassistant.feature.settings.presentation.SettingsComponent
import com.dobrihlopez.financeassistant.feature.transaction_core.expenses.presentation.ExpenseComponent
import com.dobrihlopez.financeassistant.feature.transaction_core.income.presentation.IncomeComponent
import kotlinx.serialization.Serializable

interface RootComponent {
    val state: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Accounts(val accountComponent: AccountsComponent) : Child()
        data class Income(val incomeComponent: IncomeComponent) : Child()
        data class Expenses(val expensesComponent: ExpenseComponent) : Child()
        data class Settings(val settingsComponent: SettingsComponent) : Child()
        data class Category(val categoryComponent: CategoriesComponent) : Child()
    }

    class DefaultRootComponent(
        val defaultComponentContext: ComponentContext,
        private val storeFactory: StoreFactory,
    ) : RootComponent, ComponentContext by defaultComponentContext {

        private val stack = StackNavigation<Config>()
        override val state: Value<ChildStack<*, Child>>
            get() = childStack(
                key = "RootChildStack",
                source = stack,
                initialConfiguration = Config.Expenses,
                handleBackButton = true,
                childFactory = ::child,
                serializer = Config.serializer()
            )

        private fun child(config: Config, componentContext: ComponentContext): Child {
            return when (config) {
                Config.Accounts -> Child.Accounts(AccountsComponent.DefaultAccountComponent(componentContext, storeFactory))
                Config.Categories -> Child.Category(CategoriesComponent.DefaultCategoriesComponent(componentContext, storeFactory))
                Config.Expenses -> Child.Expenses(ExpenseComponent.DefaultExpenseComponent(componentContext, storeFactory))
                Config.Income -> Child.Income(IncomeComponent.DefaultIncomeComponent(componentContext, storeFactory))
                Config.Settings -> Child.Settings(SettingsComponent.DefaultAccountComponent(componentContext, storeFactory))
            }
        }

        @Serializable
        sealed class Config {
            @Serializable
            data object Accounts : Config()
            // TODO add ids, other parameters to navigate to different screen
            @Serializable
            data object Categories : Config()
            @Serializable
            data object Expenses : Config()
            @Serializable
            data object Income : Config()
            @Serializable
            data object Settings : Config()
        }
    }
}
