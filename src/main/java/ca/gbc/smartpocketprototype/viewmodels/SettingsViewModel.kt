package ca.gbc.smartpocketprototype.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.gbc.smartpocketprototype.data.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class SettingsUiState(
    val budget: String = ""
)

class SettingsViewModel(private val repository: ExpenseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        viewModelScope.launch {
            val currentBudget = repository.userPreferences.first().monthlyBudget
            _uiState.value = SettingsUiState(budget = formatBudget(currentBudget))
        }
    }
    fun onBudgetChange(newBudget: String) {
        if (newBudget.matches(Regex("^\\d*\\.?\\d*\$"))) {
            _uiState.value = _uiState.value.copy(budget = newBudget)
            saveBudget(newBudget)
        }
    }
    private fun saveBudget(budgetString: String) {
        val budgetDouble = budgetString.toDoubleOrNull()
        if (budgetDouble != null) {
            viewModelScope.launch {
                repository.saveBudget(budgetDouble)
            }
        }
    }
    private fun formatBudget(budget: Double): String {
        return if (budget % 1 == 0.0) {
            budget.toInt().toString()
        } else {
            budget.toString()
        }
    }
}
