package ca.gbc.smartpocketprototype.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pocket_items")
data class PocketItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
