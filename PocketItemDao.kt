package ca.gbc.smartpocketprototype.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PocketItemDao {
    @Query("SELECT * FROM pocket_items ORDER BY createdAt DESC")
    fun getAll(): Flow<List<PocketItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: PocketItem): Long

    @Delete
    suspend fun delete(item: PocketItem)

    @Query("DELETE FROM pocket_items")
    suspend fun clearAll()
}
