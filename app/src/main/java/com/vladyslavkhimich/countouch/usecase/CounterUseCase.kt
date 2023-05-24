package com.vladyslavkhimich.countouch.usecase

import com.vladyslavkhimich.countouch.database.entity.Counter
import com.vladyslavkhimich.countouch.database.partials.CounterWithTag
import com.vladyslavkhimich.countouch.repository.CounterTagRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CounterUseCase @Inject constructor(
    private val counterTagRepository: CounterTagRepository
) {
    fun getCountersWithTagFlow(): Flow<List<CounterWithTag>> = counterTagRepository.getCounterWithTagsFlow()

    suspend fun updateCounter(counter: Counter) = counterTagRepository.updateCounter(counter)

    suspend fun createCounter(counter: Counter) = counterTagRepository.createCounter(counter)
}