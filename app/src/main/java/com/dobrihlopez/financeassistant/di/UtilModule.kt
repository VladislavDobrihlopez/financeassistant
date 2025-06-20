package com.dobrihlopez.financeassistant.di

import com.dobrihlopez.financeassistant.core.CoroutineDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {
    @Binds
    abstract fun bindCoroutineDispatchers(impl: CoroutineDispatchers.DefaultCoroutineDispatchers): CoroutineDispatchers
}