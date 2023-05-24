package com.vladyslavkhimich.countouch.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vladyslavkhimich.countouch.database.entity.Counter
import com.vladyslavkhimich.countouch.database.entity.Tag
import com.vladyslavkhimich.countouch.ui.theme.CountouchTheme
import java.util.Date

@Composable
fun CounterItem(
    counter: Counter,
    tag: Tag?,
    onClick: (Counter) -> Unit,
    onNumberValueChanged: (Counter, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(counter)
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    text = counter.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (tag != null)
                    TagItem(tag = tag, onClicked = {})
            }

            CounterNumber(
                counterValue = counter.count,
                stepValue = counter.step,
                onNumberValueChanged = { number -> onNumberValueChanged(counter, number) },
                isKeyboardEditAvailable = false,
                minusEnabled = true,
                plusEnabled = true
            )
        }
    }
}

@Preview
@Composable
fun CounterItemPreview() {
    CountouchTheme {
        CounterItem(counter = Counter(
            name = "This is my first Counter",
            count = 8,
            step = 1,
            creationDate = Date().time
        ), tag = Tag(name = "Games"), onClick = {}, onNumberValueChanged = { _, _ ->})
    }
}
