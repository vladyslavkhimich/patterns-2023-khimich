package com.vladyslavkhimich.countouch.presenter

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladyslavkhimich.countouch.database.entity.Counter
import com.vladyslavkhimich.countouch.database.partials.CounterWithTag
import com.vladyslavkhimich.countouch.pattern.fabricmethod.CounterSorting
import com.vladyslavkhimich.countouch.pattern.fabricmethod.CounterSortingFactory
import com.vladyslavkhimich.countouch.preferences.CountouchPreferencesInterface
import com.vladyslavkhimich.countouch.usecase.CounterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MainPresentation {
    val counters: StateFlow<List<CounterWithTag>>
    val sorting: StateFlow<CounterSorting>
    fun updateCounterValue(counter: Counter, newValue: Int)
    fun changeSorting(context: Context, sorting: CounterSorting)
}

@HiltViewModel
class MainPresenter @Inject constructor(
    private val counterUseCase: CounterUseCase,
    private val preferences: CountouchPreferencesInterface
): MainPresentation, ViewModel() {

    private val _sorting: MutableStateFlow<CounterSorting> = MutableStateFlow(
        CounterSorting.values()[preferences.getSorting()]
    )

    override val sorting: StateFlow<CounterSorting> = _sorting.asStateFlow()

    override val counters: MutableStateFlow<List<CounterWithTag>> = MutableStateFlow(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            counterUseCase.getCountersWithTagFlow().collect {
                val sortingImpl = CounterSortingFactory.getSorting(_sorting.value)
                val sorted = sortingImpl.sort(it)
                counters.value = sorted
            }
        }
    }

    override fun updateCounterValue(counter: Counter, newValue: Int) {
        val newCounter = counter.copy(count = newValue)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                counterUseCase.updateCounter(newCounter)
            }
        }
    }

    override fun changeSorting(context: Context, sorting: CounterSorting) {
        _sorting.value = sorting
        preferences.setSorting(sorting)
        sortCounters()
    }

    private fun sortCounters() {
        val sortingImpl = CounterSortingFactory.getSorting(_sorting.value)
        val sorted = sortingImpl.sort(counters.value)
        counters.value = sorted
    }

}