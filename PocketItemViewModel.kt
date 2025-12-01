package ca.gbc.smartpocketprototype.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PocketItemViewModel(private val repository: PocketItemRepository) : ViewModel() {
    val uiState: StateFlow<List<PocketItem>> = repository.items
        .map { it }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun add(title: String, description: String? = null) {
        viewModelScope.launch { repository.addOrUpdate(title, description) }
    }

    fun delete(item: PocketItem) {
        viewModelScope.launch { repository.remove(item) }
    }

    fun clearAll() {
        viewModelScope.launch { repository.clear() }
    }
}
