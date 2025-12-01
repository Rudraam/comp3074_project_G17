package ca.gbc.smartpocketprototype.ui.screens

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ca.gbc.smartpocketprototype.viewmodels.AddExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(navController: NavController, viewModel: AddExpenseViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateUp.collect {
            navController.popBackStack()
        }
    }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = uiState.amount,
                onValueChange = { viewModel.onAmountChange(it) },
                label = { Text("Amount ($)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.amountError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (uiState.amountError != null) {
                Text(uiState.amountError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(8.dp))

            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = uiState.category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    uiState.categories.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                viewModel.onCategoryChange(selectionOption)
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.notes,
                onValueChange = { viewModel.onNotesChange(it) },
                label = { Text("Notes (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = { viewModel.saveExpense() }) {
                    Text("Save")
                }
            }
        }
    }
}
