package com.vladyslavkhimich.countouch.usecase

import com.vladyslavkhimich.countouch.database.entity.Tag
import com.vladyslavkhimich.countouch.repository.CounterTagRepository
import javax.inject.Inject

class TagUseCase @Inject constructor(
    private val counterTagRepository: CounterTagRepository
) {
    suspend fun getTags() = counterTagRepository.getTags()

    suspend fun createTag(tag: Tag): Long = counterTagRepository.createTag(tag)
}