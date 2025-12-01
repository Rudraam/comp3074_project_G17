package ca.gbc.smartpocketprototype.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ca.gbc.smartpocketprototype.data.Transaction
import ca.gbc.smartpocketprototype.ui.theme.ChartGreen
import ca.gbc.smartpocketprototype.ui.theme.TextPrimary
import ca.gbc.smartpocketprototype.ui.theme.TextSecondary
import ca.gbc.smartpocketprototype.ui.theme.ChartRed
import ca.gbc.smartpocketprototype.viewmodels.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                BudgetSummaryCard(
                    totalSpent = uiState.totalSpent,
                    monthlyBudget = uiState.monthlyBudget
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Recent Transactions", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("See All")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(uiState.recentTransactions) { transaction ->
                TransactionItem(transaction = transaction)
            }
        }
    }
}

@Composable
fun BudgetSummaryCard(totalSpent: Double, monthlyBudget: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Monthly Budget", style = MaterialTheme.typography.titleMedium, color = TextSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            val spentText = String.format("$%,.2f", totalSpent)
            val budgetText = String.format("/ $%,.2f", monthlyBudget)
            val remaining = monthlyBudget - totalSpent
            val remainingText = String.format("You have $%,.2f remaining.", remaining)

            Row(verticalAlignment = Alignment.Bottom) {
                Text(spentText, fontSize = 36.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(
                    text = budgetText,
                    fontSize = 18.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(remainingText, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            val progress = if (monthlyBudget > 0) (totalSpent / monthlyBudget).toFloat().coerceIn(0f, 1f) else 0f

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.small
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = progress)
                        .height(8.dp)
                        .background(
                            color = if (progress > 0.85f) ChartRed else ChartGreen, // Change color if over budget
                            shape = MaterialTheme.shapes.small
                        )
                )
            }
        }
    }
}
@Composable
fun TransactionItem(transaction: Transaction) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        ListItem(
            headlineContent = { Text(transaction.notes, fontWeight = FontWeight.SemiBold) },
            supportingContent = { Text(transaction.category) },
            trailingContent = {
                Text(
                    text = String.format("- $%.2f", transaction.amount),
                    color = ChartRed,
                    fontWeight = FontWeight.Bold
                )
            }
        )
    }
}
