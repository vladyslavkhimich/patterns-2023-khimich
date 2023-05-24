package com.vladyslavkhimich.countouch.di

import com.vladyslavkhimich.countouch.preferences.CountouchPreferences
import com.vladyslavkhimich.countouch.preferences.CountouchPreferencesInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun bindCountouchPreferences(preferences: CountouchPreferences): CountouchPreferencesInterface
}