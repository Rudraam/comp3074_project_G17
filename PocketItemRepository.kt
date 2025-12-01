package ca.gbc.smartpocketprototype.db

import kotlinx.coroutines.flow.Flow

class PocketItemRepository(private val dao: PocketItemDao) {
    val items: Flow<List<PocketItem>> = dao.getAll()

    suspend fun addOrUpdate(title: String, description: String? = null) {
        dao.upsert(PocketItem(title = title, description = description))
    }

    suspend fun remove(item: PocketItem) {
        dao.delete(item)
    }

    suspend fun clear() {
        dao.clearAll()
    }
}
