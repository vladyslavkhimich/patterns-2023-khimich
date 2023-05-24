package com.vladyslavkhimich.countouch.di

import com.vladyslavkhimich.countouch.coordinator.MainCoordination
import com.vladyslavkhimich.countouch.coordinator.MainCoordinator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoordinationModule {

    @Binds
    abstract fun bindMainCoordination(mainCoordinator: MainCoordinator): MainCoordination
}