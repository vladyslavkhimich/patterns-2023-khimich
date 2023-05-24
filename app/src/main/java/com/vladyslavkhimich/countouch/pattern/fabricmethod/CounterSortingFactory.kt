package com.vladyslavkhimich.countouch.pattern.fabricmethod

import com.vladyslavkhimich.countouch.pattern.fabricmethod.implementation.DateNewestSorting
import com.vladyslavkhimich.countouch.pattern.fabricmethod.implementation.DateOldestSorting
import com.vladyslavkhimich.countouch.pattern.fabricmethod.implementation.NameAZSorting
import com.vladyslavkhimich.countouch.pattern.fabricmethod.implementation.NameZASorting

object CounterSortingFactory {
    fun getSorting(sorting: CounterSorting): ICounterSorting =
        when (sorting) {
            CounterSorting.CREATION_DATE_NEWEST -> DateNewestSorting()
            CounterSorting.CREATION_DATE_OLDEST -> DateOldestSorting()
            CounterSorting.NAME_A_TO_Z -> NameAZSorting()
            CounterSorting.NAME_Z_TO_A -> NameZASorting()
        }
}