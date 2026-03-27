package uk.ac.tees.mad.E4621366.mobileappdevelopment.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DropdownMenuWithLabel(
    label: String,
    options: Map<String?, String>,      // key -> display label
    selectedKey: String?,               // key currently selected (null = placeholder)
    onOptionSelected: (String?) -> Unit // returns key (nullable)
) {
    var expanded by remember { mutableStateOf(false) }

    val selectedLabel = options[selectedKey] ?: options[null] ?: "Select"

    Column {
        Text(text = label, fontWeight = FontWeight.SemiBold)

        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedLabel)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // show placeholder first if present
                if (options.containsKey(null)) {
                    DropdownMenuItem(
                        text = { Text(options[null] ?: "Select") },
                        onClick = {
                            onOptionSelected(null)
                            expanded = false
                        }
                    )
                }

                // show real options
                options
                    .filterKeys { it != null }
                    .forEach { (key, display) ->
                        DropdownMenuItem(
                            text = { Text(display) },
                            onClick = {
                                onOptionSelected(key)
                                expanded = false
                            }
                        )
                    }
            }
        }
    }
}
