package com.vladyslavkhimich.countouch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vladyslavkhimich.countouch.database.dao.CounterDao
import com.vladyslavkhimich.countouch.database.dao.TagDao
import com.vladyslavkhimich.countouch.database.entity.Counter
import com.vladyslavkhimich.countouch.database.entity.Tag

@Database(entities = [Counter::class, Tag::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun counterDao(): CounterDao
    abstract fun tagDao(): TagDao
}