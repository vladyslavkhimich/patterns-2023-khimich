package com.vladyslavkhimich.countouch.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = Tag::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("tag_id"),
        onDelete = ForeignKey.SET_NULL
    )
])
data class Counter(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "count") val count: Int,
    @ColumnInfo(name = "step") val step: Int,
    @ColumnInfo(name = "creation_date") val creationDate: Long,
    @ColumnInfo(name = "tag_id") val tagId: Long? = null
)