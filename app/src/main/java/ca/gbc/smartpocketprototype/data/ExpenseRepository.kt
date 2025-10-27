package ca.gbc.smartpocketprototype.data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val transactions: Flow<List<Transaction>> = expenseDao.getAllTransactions()

    // Handle case where preferences might not exist yet
    val userPreferences: Flow<UserPreferences> = expenseDao.getUserPreferences().map {
        it ?: UserPreferences(monthlyBudget = 2000.0) // Default budget
    }

    suspend fun saveTransaction(transaction: Transaction) {
        expenseDao.insertTransaction(transaction)
    }

    suspend fun saveBudget(budget: Double) {
        val newPreferences = UserPreferences(monthlyBudget = budget)
        expenseDao.insertUserPreferences(newPreferences)
        }
}
data class UserPreferences(val monthlyBudget: Double)
        