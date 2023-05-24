package com.vladyslavkhimich.countouch.repository

import com.vladyslavkhimich.countouch.database.dao.CounterDao
import com.vladyslavkhimich.countouch.database.dao.TagDao
import com.vladyslavkhimich.countouch.database.entity.Counter
import com.vladyslavkhimich.countouch.database.entity.Tag
import com.vladyslavkhimich.countouch.database.partials.CounterWithTag
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CounterTagRepository {
    fun getCounterWithTagsFlow(): Flow<List<CounterWithTag>>
    suspend fun updateCounter(counter: Counter)
    suspend fun createCounter(counter: Counter): Long
    suspend fun getTags(): List<Tag>
    suspend fun createTag(tag: Tag): Long
}

class CounterTagRepositoryImpl @Inject constructor(
    private val counterDao: CounterDao,
    private val tagDao: TagDao
): CounterTagRepository {
    override fun getCounterWithTagsFlow(): Flow<List<CounterWithTag>> = counterDao.getCountersWithTag()

    override suspend fun updateCounter(counter: Counter) = counterDao.update(counter)

    override suspend fun createCounter(counter: Counter): Long = counterDao.insert(counter)

    override suspend fun getTags(): List<Tag> = tagDao.getTags()

    override suspend fun createTag(tag: Tag): Long = tagDao.insert(tag)
}