package ca.gbc.smartpocketprototype.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.gbc.smartpocketprototype.data.ExpenseRepository
import ca.gbc.smartpocketprototype.data.Transaction
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class AllTransactionsUiState(
    val transactions: List<Transaction> = emptyList()
)

class AllTransactionsViewModel(repository: ExpenseRepository) : ViewModel() {

    val uiState: StateFlow<AllTransactionsUiState> = repository.transactions
        .map { transactions ->
            AllTransactionsUiState(transactions = transactions.sortedByDescending { it.timestamp })
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AllTransactionsUiState()
        )
}
