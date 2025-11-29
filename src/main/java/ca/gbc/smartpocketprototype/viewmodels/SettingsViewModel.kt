package ca.gbc.smartpocketprototype.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.gbc.smartpocketprototype.data.ExpenseRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class SettingsUiState(
    val budget: String = "",
    val categories: List<String> = emptyList()
)

class SettingsViewModel(private val repository: ExpenseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    private val _budgetSaved = MutableSharedFlow<Unit>()
    val budgetSaved = _budgetSaved.asSharedFlow()

    private val _dataCleared = MutableSharedFlow<Unit>()
    val dataCleared = _dataCleared.asSharedFlow()

    init {
        viewModelScope.launch {
            repository.userPreferences.collect { prefs ->
                _uiState.value = SettingsUiState(
                    budget = formatBudget(prefs.monthlyBudget),
                    categories = prefs.categories
                )
            }
        }
    }
    fun onBudgetChange(newBudget: String) {
        if (newBudget.matches(Regex("^\\d*\\.?\\d*\$"))) {
            _uiState.value = _uiState.value.copy(budget = newBudget)
        }
    }

    fun saveBudget() {
        val budgetString = _uiState.value.budget
        val budgetDouble = budgetString.toDoubleOrNull()
        if (budgetDouble != null) {
            viewModelScope.launch {
                repository.saveBudget(budgetDouble)
                _budgetSaved.emit(Unit)
            }
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            repository.clearAllData()
            _dataCleared.emit(Unit)
        }
    }

    fun addCategory(category: String) {
        viewModelScope.launch {
            repository.addCategory(category)
        }
    }

    fun removeCategory(category: String) {
        viewModelScope.launch {
            repository.removeCategory(category)
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
