package com.vladyslavkhimich.countouch.ui.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vladyslavkhimich.countouch.R
import com.vladyslavkhimich.countouch.ui.theme.CountouchTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldWithPlaceholder(
    value: String,
    onValueChanged: (String) -> Unit,
    @StringRes
    placeholder: Int,
    @StringRes
    fieldPlaceholder: Int,
    keyboardOptions: KeyboardOptions,
    isEditing: Boolean,
    isSingleLine: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (isEditing) {
            TextField(
                value = value,
                onValueChange = onValueChanged,
                placeholder = { Text(text = stringResource(id = fieldPlaceholder)) },
                singleLine = isSingleLine,
                keyboardOptions = keyboardOptions,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            if (placeholder != 0) {
                Text(
                    text = stringResource(id = placeholder),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            Text(text = value, fontSize = 16.sp)
        }
    }
}

@Preview
@Composable
fun FieldWithPlaceholderPreview() {
    CountouchTheme() {
        FieldWithPlaceholder(
            value = "",
            onValueChanged = { },
            placeholder = R.string.label_name,
            fieldPlaceholder = R.string.label_name,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isEditing = true,
            isSingleLine = true
        )
    }
}
