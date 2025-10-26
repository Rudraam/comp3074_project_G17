package ca.gbc.smartpocketprototype.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.gbc.smartpocketprototype.ui.theme.ChartGreen
import ca.gbc.smartpocketprototype.ui.theme.TextPrimary
import ca.gbc.smartpocketprototype.ui.theme.TextSecondary
import ca.gbc.smartpocketprototype.ui.theme.ChartRed // <-- ADD THIS IMPORT STATEMENT

@Composable
fun HomeScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("Welcome Back!", style = MaterialTheme.typography.headlineSmall, color = TextSecondary)
            Text("Samantha", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            BudgetSummaryCard()
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recent Transactions", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                TextButton(onClick = { /* Navigate to all transactions */ }) {
                    Text("See All")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(5) { index ->
            TransactionItem(index)
        }
    }
}

@Composable
fun BudgetSummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Monthly Budget", style = MaterialTheme.typography.titleMedium, color = TextSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text("$1,250", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(
                    text = " / $2,000",
                    fontSize = 18.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("You have $750 remaining.", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))


            val progress = 1250f / 2000f // The fraction of the bar to fill

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    // The background of the bar (the track)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.small // Use small for rounded corners
                    )
            ) {
                Box(
                    modifier = Modifier
                        // Fill a fraction of the width based on progress
                        .fillMaxWidth(fraction = progress)
                        .height(8.dp)
                        // The foreground of the bar (the progress)
                        .background(
                            color = ChartGreen,
                            shape = MaterialTheme.shapes.small
                        )
                )
            }

        }
    }
}


@Composable
fun TransactionItem(index: Int) {
    val dummyData = listOf(
        "Tim Hortons" to "Food",
        "TTC Presto" to "Travel",
        "Netflix" to "Entertainment",
        "NoFrills" to "Groceries",
        "Amazon" to "Shopping"
    )
    val amount = listOf("- $8.75", "- $3.35", "- $16.99", "- $72.50", "- $45.99")
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        ListItem(
            headlineContent = { Text(dummyData[index].first, fontWeight = FontWeight.SemiBold) },
            supportingContent = { Text(dummyData[index].second) },
            trailingContent = { Text(amount[index], color = ChartRed, fontWeight = FontWeight.Bold) }
        )
    }
}
