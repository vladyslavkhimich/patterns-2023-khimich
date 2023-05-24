package com.vladyslavkhimich.countouch.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.vladyslavkhimich.countouch.database.entity.Counter
import com.vladyslavkhimich.countouch.database.partials.CounterWithTag
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterDao {

    @Transaction
    @Query("SELECT * FROM Counter")
    fun getCountersWithTag(): Flow<List<CounterWithTag>>

    @Update
    suspend fun update(counter: Counter)

    @Insert
    suspend fun insert(counter: Counter): Long

}