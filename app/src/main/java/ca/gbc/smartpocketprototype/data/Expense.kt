// In: app/src/main/java/ca/gbc/smartpocketprototype/data/model/Expense.kt
package ca.gbc.smartpocketprototype.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses") // Defines the table name
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val category: String,
    val description: String,
    val date: Long // Storing date as a Long (timestamp) is efficient
)
