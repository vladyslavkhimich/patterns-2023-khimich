package com.vladyslavkhimich.countouch.di

import android.content.Context
import androidx.room.Room
import com.vladyslavkhimich.countouch.database.AppDatabase
import com.vladyslavkhimich.countouch.database.callback.FirstCreateCallback
import com.vladyslavkhimich.countouch.database.dao.CounterDao
import com.vladyslavkhimich.countouch.database.dao.TagDao
import com.vladyslavkhimich.countouch.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context,
        provider: Provider<TagDao>
    ): AppDatabase = Room.databaseBuilder(appContext, AppDatabase::class.java, Constants.DATABASE_NAME)
        .addCallback(FirstCreateCallback(appContext, provider))
        .build()

    @Provides
    fun provideCounterDao(appDatabase: AppDatabase): CounterDao = appDatabase.counterDao()

    @Provides
    fun provideTagDao(appDatabase: AppDatabase): TagDao = appDatabase.tagDao()
}