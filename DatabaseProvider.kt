package ca.gbc.smartpocketprototype.db

import android.content.Context

object DatabaseProvider {
    @Volatile private var repository: PocketItemRepository? = null

    fun pocketItemRepository(context: Context): PocketItemRepository = repository ?: synchronized(this) {
        repository ?: PocketItemRepository(AppDatabase.get(context).pocketItemDao()).also { repository = it }
    }
}
