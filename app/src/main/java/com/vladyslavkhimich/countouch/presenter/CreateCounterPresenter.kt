package com.vladyslavkhimich.countouch.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladyslavkhimich.countouch.database.entity.Counter
import com.vladyslavkhimich.countouch.database.entity.Tag
import com.vladyslavkhimich.countouch.model.interfaces.TemplateStrategy
import com.vladyslavkhimich.countouch.usecase.CounterUseCase
import com.vladyslavkhimich.countouch.usecase.TagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

interface CreateCounterPresentation {
    val uiState: StateFlow<CreateCounterUiState>
    val createCounterState: Flow<CreateState>
    fun createCounter()
    fun onNameChanged(name: String)
    fun onInitialValueChanged(value: Int)
    fun onStepChanged(step: Int)
    fun onTagChanged(tagName: String)
    fun onProposedTagChanged(tag: Tag?)
}

@HiltViewModel
class CreateCounterPresenter @Inject constructor(
    private val counterUseCase: CounterUseCase,
    private val tagUseCase: TagUseCase
): CreateCounterPresentation, ViewModel() {
    private val _uiState = MutableStateFlow(CreateCounterUiState())

    override val uiState: StateFlow<CreateCounterUiState>
        get() = _uiState

    override val createCounterState = MutableStateFlow<CreateState>(CreateState.Initial)

    init {
        loadTags()
    }

    private fun loadTags() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val tags = tagUseCase.getTags()
                _uiState.value = _uiState.value.copy(proposedTags = tags)
            }
        }
    }

    override fun createCounter() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                with (_uiState.value) {
                    val foundTag = proposedTags.find { it.name == tag?.name }
                    val tagId = foundTag?.id
                        ?: if (tag != null) {
                            tagUseCase.createTag(Tag(name = tag.name))
                        } else {
                            null
                        }

                    val newCounter = Counter(
                        name = name,
                        count = initialValue,
                        step = step,
                        creationDate = Date().time,
                        tagId = tagId
                    )

                    createCounterState.value = CreateState.Loading

                    val insertedId = counterUseCase.createCounter(newCounter)

                    if (insertedId != -1L)
                        createCounterState.value = CreateState.Success
                    else
                        createCounterState.value = CreateState.Failed
                }
            }
        }
    }

    override fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    override fun onInitialValueChanged(value: Int) {
        _uiState.value = _uiState.value.copy(initialValue = value)
    }

    override fun onStepChanged(step: Int) {
        _uiState.value = _uiState.value.copy(step = step)
    }

    override fun onTagChanged(tagName: String) {
        val newTag = Tag(name = tagName)
        _uiState.value = _uiState.value.copy(tag = newTag)
    }

    override fun onProposedTagChanged(tag: Tag?) {
        _uiState.value = _uiState.value.copy(tag = tag)
    }
}

data class CreateCounterUiState(
    val name: String = "",
    val initialValue: Int = 0,
    val step: Int = 1,
    val tag: Tag? = null,
    val proposedTags: List<Tag> = emptyList(),
    val randomTemplate: TemplateStrategy? = null
)

sealed class CreateState {
    object Initial: CreateState()
    object Loading: CreateState()
    object Success: CreateState()
    object ValidationErrors : CreateState()
    object Failed: CreateState()
}