package ca.gbc.smartpocketprototype.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(    @PrimaryKey(autoGenerate = true)
                           val id: Int = 0,
                           val amount: Double,
                           val category: String,
                           val notes: String,
                           val timestamp: Long
)

@Entity(tableName = "user_preferences")
// FIX: Completed the class name from "UserPrefe" to "UserPreferences"
data class UserPreferences(
    // We use a fixed ID to ensure there's only one row for user preferences.
    @PrimaryKey
    val id: Int = 1,
    val monthlyBudget:Double
)
