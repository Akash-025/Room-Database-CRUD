package com.example.animal_room_crud.CategorySpinner

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySpinner(selectedItem: String, onItemSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) } // Controls menu visibility

    val items = listOf("Mammals", "Birds", "Reptiles", "Amphibians", "Fish", "Arthropoda")

    Column(
        modifier = Modifier
    ) {
        // Dropdown Box
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it } // Toggle dropdown visibility
        ) {
            // TextField as the Dropdown trigger
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true, // Make the field read-only
                label = { Text("Category") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .width(163.dp)
                    .menuAnchor() // Required for positioning
            )

            // Dropdown Menu
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false } // Close the dropdown when clicked outside
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            onItemSelected(item) // Call the callback to update selectedItem
                            expanded = false // Close the menu after selecting
                        },
                        text = { Text(item) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpinnerExamplePreview() {
    var selectedItem by remember { mutableStateOf("Select class") }

    CategorySpinner(
        selectedItem = selectedItem,
        onItemSelected = { selectedItem = it }
    )
}
