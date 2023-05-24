package com.vladyslavkhimich.countouch.pattern.fabricmethod.implementation

import com.vladyslavkhimich.countouch.database.partials.CounterWithTag
import com.vladyslavkhimich.countouch.pattern.fabricmethod.ICounterSorting

class NameAZSorting: ICounterSorting {
    override fun sort(counters: List<CounterWithTag>) = counters.sortedBy { it.counter.name }
}