package ca.gbc.smartpocketprototype.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
class ExpenseRepository {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: Flow<List<Transaction>> = _transactions

    private val _userPreferences = MutableStateFlow(UserPreferences(monthlyBudget = 2000.0))
    val userPreferences: Flow<UserPreferences> = _userPreferences

    fun addExpense(transaction: Transaction) {
        _transactions.update { currentList ->
            currentList + transaction
        }
    }

    fun saveBudget(newBudget: Double) {
        _userPreferences.update { it.copy(monthlyBudget = newBudget) }
    }
    companion object {
        @Volatile private var INSTANCE: ExpenseRepository? = null

        fun getInstance(): ExpenseRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ExpenseRepository().also { INSTANCE = it }
            }
        }
    }
}

data class UserPreferences(val monthlyBudget: Double)
