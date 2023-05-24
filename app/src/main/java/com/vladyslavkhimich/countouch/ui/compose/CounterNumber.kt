package com.vladyslavkhimich.countouch.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vladyslavkhimich.countouch.R
import com.vladyslavkhimich.countouch.ui.theme.CountouchTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterNumber(
    counterValue: Int,
    stepValue: Int,
    onNumberValueChanged: (Int) -> Unit,
    isKeyboardEditAvailable: Boolean,
    minusEnabled: Boolean,
    plusEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Button(
            onClick = { onNumberValueChanged(counterValue - stepValue) },
            modifier = Modifier
                .size(48.dp),
            enabled = minusEnabled,
            contentPadding = PaddingValues(1.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_minus),
                contentDescription = stringResource(id = R.string.content_description_minus_counter))
        }

        Spacer(modifier = Modifier.width(12.dp))

        val textValue = counterValue.toString()

        if (isKeyboardEditAvailable) {
            TextField(
                value = textValue,
                onValueChange = { onNumberValueChanged(it.toIntOrNull() ?: 0) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.widthIn(64.dp, 128.dp)
            )
        } else {
            Text(
                text = textValue,
                fontSize = 19.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = { onNumberValueChanged(counterValue + stepValue) },
            modifier = Modifier
                .size(48.dp),
            enabled = plusEnabled,
            contentPadding = PaddingValues(1.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = stringResource(id = R.string.content_description_minus_counter))
        }
    }
}

@Preview
@Composable
fun CounterNumberPreview() {
    CountouchTheme() {
        CounterNumber(
            counterValue = 3,
            stepValue = 1,
            onNumberValueChanged = {},
            isKeyboardEditAvailable = false,
            minusEnabled = true,
            plusEnabled = true,
            modifier = Modifier
        )
    }
}
