package com.vladyslavkhimich.countouch.pattern.fabricmethod.implementation

import com.vladyslavkhimich.countouch.database.partials.CounterWithTag
import com.vladyslavkhimich.countouch.pattern.fabricmethod.ICounterSorting

class DateNewestSorting: ICounterSorting {
    override fun sort(counters: List<CounterWithTag>) = counters.sortedByDescending { it.counter.creationDate }
}