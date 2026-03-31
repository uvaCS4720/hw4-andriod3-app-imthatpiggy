package edu.nd.pmcburne.hello

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.exp

@Composable
fun Dropdown(
    tags: List<String>,
    selected: String,
    onSelected: (String) -> Unit) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier
            .padding(16.dp, top=20.dp, end = 16.dp, bottom = 12.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        Button(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    buttonSize = coordinates.size
                }
        ) {
            Text(selected)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { buttonSize.width.toDp() })
                .heightIn(max = screenHeight / 2)
        ) {
            tags.forEach { tag ->
                DropdownMenuItem(
                    text = { Text(tag) },
                    onClick = {
                        onSelected(tag)
                        expanded = false
                    }
                )
            }
        }
    }
}