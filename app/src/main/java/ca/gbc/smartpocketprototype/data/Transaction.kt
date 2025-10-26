package ca.gbc.smartpocketprototype.data

data class Transaction(
    val id: Long,
    val amount: Double,
    val category: String,
    val notes: String,
    val timestamp: Long = System.currentTimeMillis()
)
        