package com.vladyslavkhimich.countouch.pattern.fabricmethod

import com.vladyslavkhimich.countouch.database.partials.CounterWithTag

interface ICounterSorting {
    fun sort(counters: List<CounterWithTag>): List<CounterWithTag>
}