package com.vladyslavkhimich.countouch.preferences

import com.vladyslavkhimich.countouch.pattern.fabricmethod.CounterSorting

interface CountouchPreferencesInterface {
    fun setSorting(sorting: CounterSorting)
    fun getSorting(): Int
}