package ca.gbc.smartpocketprototype.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ca.gbc.smartpocketprototype.data.ExpenseRepository

class ViewModelFactory(private val repository: ExpenseRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddExpenseViewModel::class.java) -> {
                AddExpenseViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ReportsViewModel::class.java) -> {
                ReportsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AllTransactionsViewModel::class.java) -> {
                AllTransactionsViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
