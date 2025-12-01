package ca.gbc.smartpocketprototype.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.gbc.smartpocketprototype.data.ExpenseRepository
import ca.gbc.smartpocketprototype.data.Transaction
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class HomeUiState(
    val totalSpent: Double = 0.0,
    val monthlyBudget: Double = 2000.0,
    val recentTransactions: List<Transaction> = emptyList()
)

class HomeViewModel(private val repository: ExpenseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            combine(repository.transactions, repository.userPreferences) { transactions, prefs ->
                HomeUiState(
                    totalSpent = transactions.sumOf { it.amount },
                    monthlyBudget = prefs.monthlyBudget,
                    recentTransactions = transactions.sortedByDescending { it.timestamp }.take(5)
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }
}
