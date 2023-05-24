package com.vladyslavkhimich.countouch.ui.compose

import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vladyslavkhimich.countouch.database.entity.Tag
import com.vladyslavkhimich.countouch.ui.theme.CountouchTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagItem(
    tag: Tag,
    onClicked: (Tag) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedAssistChip(
        onClick = { onClicked(tag) },
        label = { Text(tag.name) },
        modifier = modifier
    )
}

@Composable
@Preview
fun TagItemPreview() {
    CountouchTheme() {
        TagItem(tag = Tag(name = "Medicine"), onClicked = {}, modifier = Modifier)
    }
}
