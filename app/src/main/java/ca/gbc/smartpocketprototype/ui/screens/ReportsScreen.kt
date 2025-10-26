package ca.gbc.smartpocketprototype.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.gbc.smartpocketprototype.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("By Category", "Calendar View")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Spending Reports", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> CategoryReport()
                1 -> CalendarReport()
            }
        }
    }
}

@Composable
fun CategoryReport() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Spending by Category (October)", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(24.dp))


        Box(
            modifier = Modifier
                .size(250.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.extraLarge),
            contentAlignment = Alignment.Center
        ) {
            Text("Pie Chart Placeholder", color = TextSecondary)
        }
        Spacer(Modifier.height(24.dp))

        // Legend
        CategoryLegendItem("Food (45%)", ChartRed)
        CategoryLegendItem("Travel (20%)", ChartYellow)
        CategoryLegendItem("Utilities (15%)", PrimaryBlue)
        CategoryLegendItem("Entertainment (20%)", ChartPurple)
    }
}

@Composable
fun CategoryLegendItem(text: String, color: androidx.compose.ui.graphics.Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, shape = MaterialTheme.shapes.small)
        )
        Spacer(Modifier.width(16.dp))
        Text(text, fontSize = 16.sp)
    }
}

@Composable
fun CalendarReport() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Spending by Day (October)", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Calendar View Placeholder", color = TextSecondary)
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("This could show daily spending totals.", color = TextSecondary)
    }
}
