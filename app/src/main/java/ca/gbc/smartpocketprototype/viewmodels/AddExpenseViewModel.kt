package ca.gbc.smartpocketprototype.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.gbc.smartpocketprototype.data.ExpenseRepository
import ca.gbc.smartpocketprototype.data.Transaction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

data class AddExpenseUiState(
    val amount: String = "",
    val category: String = "Food",
    val notes: String = "",
    val amountError: String? = null,
    val categories: List<String> = listOf("Food", "Travel", "Entertainment", "Utilities", "Groceries", "Other")
)

class AddExpenseViewModel(private val repository: ExpenseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AddExpenseUiState())
    val uiState: StateFlow<AddExpenseUiState> = _uiState

    private val _navigateUp = MutableSharedFlow<Unit>()
    val navigateUp = _navigateUp.asSharedFlow()

    fun onAmountChange(newAmount: String) {
        // Allow only numbers and a single decimal point.
        if (newAmount.matches(Regex("^\\d*\\.?\\d*\$"))) {
            _uiState.value = _uiState.value.copy(amount = newAmount, amountError = null)
        }
    }

    fun onCategoryChange(newCategory: String) {
        _uiState.value = _uiState.value.copy(category = newCategory)
    }

    fun onNotesChange(newNotes: String) {
        _uiState.value = _uiState.value.copy(notes = newNotes)
    }

    fun saveExpense() {
        val amountStr = _uiState.value.amount
        val amountDouble = amountStr.toDoubleOrNull()

        if (amountDouble == null || amountDouble <= 0) {
            _uiState.value = _uiState.value.copy(amountError = "Please enter a valid amount.")
            return
        }

        if (_uiState.value.notes.isBlank()) {
            _uiState.value = _uiState.value.copy(notes = _uiState.value.category)
        }

        // --- Save Data ---
        viewModelScope.launch {
            val newTransaction = Transaction(
                id = System.currentTimeMillis(),
                amount = amountDouble,
                category = _uiState.value.category,
                notes = _uiState.value.notes
            )
            repository.addExpense(newTransaction)
            _navigateUp.emit(Unit)
        }
    }
}