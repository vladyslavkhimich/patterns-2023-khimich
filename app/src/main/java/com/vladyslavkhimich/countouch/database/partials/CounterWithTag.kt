package com.vladyslavkhimich.countouch.database.partials

import androidx.room.Embedded
import androidx.room.Relation
import com.vladyslavkhimich.countouch.database.entity.Counter
import com.vladyslavkhimich.countouch.database.entity.Tag

data class CounterWithTag(
    @Embedded val counter: Counter,
    @Relation(
        parentColumn = "tag_id",
        entityColumn = "id"
    )
    val tag: Tag?
)