package com.vladyslavkhimich.countouch.pattern.fabricmethod.implementation

import com.vladyslavkhimich.countouch.database.partials.CounterWithTag
import com.vladyslavkhimich.countouch.pattern.fabricmethod.ICounterSorting

class DateOldestSorting: ICounterSorting {
    override fun sort(counters: List<CounterWithTag>) = counters.sortedBy { it.counter.creationDate }
}