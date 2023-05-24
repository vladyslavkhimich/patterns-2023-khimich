package com.vladyslavkhimich.countouch.preferences

import android.content.Context
import android.content.SharedPreferences
import com.vladyslavkhimich.countouch.pattern.fabricmethod.CounterSorting
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CountouchPreferences @Inject constructor(
    @ApplicationContext private val context: Context
): CountouchPreferencesInterface {

    companion object {
        const val DEFAULT_SHARED_PREFS = "DefaultSharedPrefs"

        const val COUNTER_SORTING_PREFS = "counterSorting"
    }

    override fun setSorting(sorting: CounterSorting) {
        getPreferences().edit().putInt(COUNTER_SORTING_PREFS, sorting.ordinal).apply()
    }

    override fun getSorting(): Int {
        return getPreferences().getInt(COUNTER_SORTING_PREFS, 0)
    }

    private fun getPreferences(): SharedPreferences {
        return context.getSharedPreferences(DEFAULT_SHARED_PREFS, Context.MODE_PRIVATE)
    }
}