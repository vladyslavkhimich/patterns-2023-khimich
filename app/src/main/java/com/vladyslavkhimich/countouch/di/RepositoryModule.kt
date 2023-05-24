package com.vladyslavkhimich.countouch.di

import com.vladyslavkhimich.countouch.repository.CounterTagRepository
import com.vladyslavkhimich.countouch.repository.CounterTagRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCounterRepository(counterRepositoryImpl: CounterTagRepositoryImpl): CounterTagRepository
}