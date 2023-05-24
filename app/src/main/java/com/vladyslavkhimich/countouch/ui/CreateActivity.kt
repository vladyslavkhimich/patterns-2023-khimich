package com.vladyslavkhimich.countouch.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vladyslavkhimich.countouch.R
import com.vladyslavkhimich.countouch.database.entity.Tag
import com.vladyslavkhimich.countouch.model.interfaces.TemplateStrategy
import com.vladyslavkhimich.countouch.presenter.CreateCounterPresentation
import com.vladyslavkhimich.countouch.presenter.CreateCounterPresenter
import com.vladyslavkhimich.countouch.presenter.CreateCounterUiState
import com.vladyslavkhimich.countouch.presenter.CreateState
import com.vladyslavkhimich.countouch.ui.compose.CounterNumber
import com.vladyslavkhimich.countouch.ui.compose.FieldWithPlaceholder
import com.vladyslavkhimich.countouch.ui.compose.TagItem
import com.vladyslavkhimich.countouch.ui.theme.CountouchTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateActivity: ComponentActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CreateActivity::class.java)
        }
    }

    private val presenter: CreateCounterPresentation by viewModels<CreateCounterPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CountouchTheme {
                val uiState by presenter.uiState.collectAsState()

                CreateScreen(
                    uiState = uiState,
                    onNameValueChanged = presenter::onNameChanged,
                    onInitialValueChanged = presenter::onInitialValueChanged,
                    onStepChanged = presenter::onStepChanged,
                    onTagChanged = presenter::onTagChanged,
                    onProposedTagChanged = presenter::onProposedTagChanged,
                    onCreateClick = presenter::createCounter
                )
            }
        }

        setListeners()
    }

    private fun setListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                presenter.createCounterState.collect { createState ->
                    when (createState) {
                        is CreateState.Success -> { finish() }
                        is CreateState.Failed -> { }
                        is CreateState.ValidationErrors -> { }
                        else -> { }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    uiState: CreateCounterUiState,
    onNameValueChanged: (String) -> Unit,
    onInitialValueChanged: (Int) -> Unit,
    onStepChanged: (Int) -> Unit,
    onTagChanged: (String) -> Unit,
    onProposedTagChanged: (Tag?) -> Unit,
    onCreateClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.create_screen_top_bar))
                },
                actions = {
                    IconButton(onClick = onCreateClick) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(id = R.string.content_description_create_counter)
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(modifier =
            Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                )
                .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                FieldWithPlaceholder(
                    value = uiState.name,
                    onValueChanged = onNameValueChanged,
                    placeholder = R.string.label_name,
                    fieldPlaceholder = R.string.label_name,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    isEditing = true,
                    isSingleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Text(
                    text = stringResource(id = R.string.label_initial_value),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                CounterNumber(
                    counterValue = uiState.initialValue,
                    stepValue = uiState.step,
                    onNumberValueChanged = onInitialValueChanged,
                    isKeyboardEditAvailable = true,
                    minusEnabled = true,
                    plusEnabled = true,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.label_step),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                CounterNumber(
                    counterValue = uiState.step,
                    stepValue = uiState.step,
                    onNumberValueChanged = onStepChanged,
                    isKeyboardEditAvailable = true,
                    minusEnabled = true,
                    plusEnabled = true,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.and_add_a_tag),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                FieldWithPlaceholder(
                    value = uiState.tag?.name ?: "",
                    onValueChanged = onTagChanged,
                    placeholder = 0,
                    fieldPlaceholder = R.string.label_tag,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    isEditing = true,
                    isSingleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.or_select_existing_one),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                LazyRow(content = {
                    uiState.proposedTags.forEach {
                        item {
                            TagItem(tag = it, onClicked = onProposedTagChanged, modifier = Modifier.padding(end = 8.dp))
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = { onCreateClick() }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.label_create))
                }
            }
        }
    )
}

@Preview
@Composable
fun CreateScreenPreview() {
    CountouchTheme() {
        CreateScreen(
            uiState = CreateCounterUiState(
                name = "Test name",
                initialValue = 0,
                step = 1,
                tag = null,
                proposedTags = listOf(
                    Tag(name = "Medicine"),
                    Tag(name = "Books"),
                    Tag(name = "Games"),
                    Tag(name = "Drinks")
                ),
                randomTemplate = object: TemplateStrategy {
                    override fun getName(): String = "Munchkin"

                    override fun getInitialCount(): Int = 1

                    override fun getStep(): Int  = 1

                    override fun getTag(): Tag = Tag(name = "Games")

                }
            ),
            onNameValueChanged = {},
            onInitialValueChanged = {},
            onStepChanged = {},
            onTagChanged = {},
            onProposedTagChanged = {}
        ) {

        }
    }
}
