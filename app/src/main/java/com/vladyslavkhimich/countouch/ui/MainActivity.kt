package com.vladyslavkhimich.countouch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vladyslavkhimich.countouch.R
import com.vladyslavkhimich.countouch.coordinator.MainCoordination
import com.vladyslavkhimich.countouch.database.entity.Counter
import com.vladyslavkhimich.countouch.database.partials.CounterWithTag
import com.vladyslavkhimich.countouch.pattern.fabricmethod.CounterSorting
import com.vladyslavkhimich.countouch.presenter.MainPresentation
import com.vladyslavkhimich.countouch.presenter.MainPresenter
import com.vladyslavkhimich.countouch.ui.compose.CounterItem
import com.vladyslavkhimich.countouch.ui.theme.CountouchTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val presenter: MainPresentation by viewModels<MainPresenter>()

    @Inject
    lateinit var coordinator: MainCoordination

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountouchTheme {
                // A surface container using the 'background' color from the theme
                val countersWithTag = presenter.counters.collectAsState(initial = emptyList())
                val counterSorting = presenter.sorting.collectAsState()
                MainScreen(
                    countersWithTag = countersWithTag.value,
                    counterSorting = counterSorting.value,
                    onFabClick = { coordinator.navigateToCreateCounter(this) },
                    onCounterItemClick = { counter ->
                        coordinator.navigateToEditCounter(
                            this,
                            counter.id
                        )
                    },
                    onCounterValueChanged = presenter::updateCounterValue,
                    onCounterSortingClick = { presenter.changeSorting(this, it) }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    countersWithTag: List<CounterWithTag>,
    counterSorting: CounterSorting,
    onFabClick: () -> Unit,
    onCounterItemClick: (Counter) -> Unit,
    onCounterValueChanged: (Counter, Int) -> Unit,
    onCounterSortingClick: (CounterSorting) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ) {
            if (countersWithTag.isNotEmpty()) {
                LazyColumn {
                    item {
                        Row(
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.header_your_counters),
                                style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp),
                                modifier = Modifier.weight(1f).padding(end = 12.dp),
                                maxLines = 2,
                            )

                            if (countersWithTag.size > 1) {
                                FilterDropdown(
                                    counterSorting = counterSorting,
                                    onCounterSortingClick = onCounterSortingClick
                                )
                            }
                        }
                    }

                    items(countersWithTag) { counterWithTag ->
                        CounterItem(
                            counter = counterWithTag.counter,
                            tag = counterWithTag.tag,
                            onClick = onCounterItemClick,
                            onNumberValueChanged = onCounterValueChanged,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(72.dp))
                    }
                }
            } else {
                Text(
                    text = stringResource(id = R.string.empty_counter_list_placeholder),
                    modifier = Modifier
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            FloatingActionButton(
                onClick = onFabClick,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.content_description_create_counter)
                )
            }
        }
    }
}

@Composable
fun FilterDropdown(
    counterSorting: CounterSorting,
    onCounterSortingClick: (CounterSorting) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                expanded = !expanded
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_sort),
                contentDescription = null,
                modifier = Modifier.padding(end = 4.dp)
            )

            val sortingPart = when (counterSorting) {
                CounterSorting.CREATION_DATE_NEWEST -> stringResource(id = R.string.sorted_creation_date_newest)
                CounterSorting.CREATION_DATE_OLDEST -> stringResource(id = R.string.sorted_creation_date_oldest)
                CounterSorting.NAME_A_TO_Z -> stringResource(id = R.string.sorted_name_a_to_z)
                CounterSorting.NAME_Z_TO_A -> stringResource(id = R.string.sorted_name_z_to_a)
            }

            Text(
                text = stringResource(
                    id = R.string.sort_placeholder,
                    sortingPart
                )
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(text = { Text(stringResource(id = R.string.sorted_creation_date_newest)) }, onClick = { onCounterSortingClick(CounterSorting.CREATION_DATE_NEWEST) })
            DropdownMenuItem(text = { Text(stringResource(id = R.string.sorted_creation_date_oldest)) }, onClick = { onCounterSortingClick(CounterSorting.CREATION_DATE_OLDEST) })
            DropdownMenuItem(text = { Text(stringResource(id = R.string.sorted_name_a_to_z)) }, onClick = { onCounterSortingClick(CounterSorting.NAME_A_TO_Z) })
            DropdownMenuItem(text = { Text(stringResource(id = R.string.sorted_name_z_to_a)) }, onClick = { onCounterSortingClick(CounterSorting.NAME_Z_TO_A) })
        }

    }
}
