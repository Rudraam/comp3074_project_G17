package ca.gbc.smartpocketprototype.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
class ExpenseRepository {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: Flow<List<Transaction>> = _transactions

    private val initialCategories = listOf("Food", "Travel", "Entertainment", "Utilities", "Groceries", "Other")
    private val _userPreferences = MutableStateFlow(UserPreferences(monthlyBudget = 2000.0, categories = initialCategories))
    val userPreferences: Flow<UserPreferences> = _userPreferences

    fun addExpense(transaction: Transaction) {
        _transactions.update { currentList ->
            currentList + transaction
        }
    }

    fun saveBudget(newBudget: Double) {
        _userPreferences.update { it.copy(monthlyBudget = newBudget) }
    }

    fun addCategory(category: String) {
        _userPreferences.update { it.copy(categories = it.categories + category) }
    }

    fun removeCategory(category: String) {
        _userPreferences.update { it.copy(categories = it.categories - category) }
    }

    fun clearAllData() {
        _transactions.update { emptyList() }
        _userPreferences.update { UserPreferences(monthlyBudget = 2000.0, categories = initialCategories) }
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

data class UserPreferences(val monthlyBudget: Double, val categories: List<String>)
