package com.vladyslavkhimich.countouch.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vladyslavkhimich.countouch.database.entity.Tag

@Dao
interface TagDao {

    @Insert
    suspend fun insert(tag: Tag): Long

    @Insert
    suspend fun insertAll(tags: List<Tag>)

    @Query("SELECT * FROM Tag")
    suspend fun getTags(): List<Tag>

}