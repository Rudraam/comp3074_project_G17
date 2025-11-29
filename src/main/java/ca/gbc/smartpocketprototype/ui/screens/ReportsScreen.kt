package ca.gbc.smartpocketprototype.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ca.gbc.smartpocketprototype.ui.theme.ChartRed
import ca.gbc.smartpocketprototype.ui.theme.TextSecondary
import ca.gbc.smartpocketprototype.viewmodels.CalendarDayData
import ca.gbc.smartpocketprototype.viewmodels.PieChartData
import ca.gbc.smartpocketprototype.viewmodels.ReportsViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(viewModel: ReportsViewModel) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("By Category", "Calendar View")

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                0 -> CategoryReport(pieChartData = uiState.pieChartData, totalSpending = uiState.totalSpending)
                1 -> CustomCalendarReport(calendarData = uiState.calendarData)
            }
        }
    }
}
@Composable
fun CategoryReport(pieChartData: List<PieChartData>, totalSpending: Double) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Spending by Category", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text(String.format("Total: $%,.2f", totalSpending), style = MaterialTheme.typography.titleMedium, color = TextSecondary)
        Spacer(Modifier.height(24.dp))
        if (pieChartData.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No spending data yet!", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
        } else {
            ManualPieChart(data = pieChartData, modifier = Modifier.size(250.dp))
            Spacer(Modifier.height(32.dp))
            pieChartData.forEach { data ->
                val percentage = data.value * 100
                CategoryLegendItem(text = "${data.category} (${"%.1f".format(percentage)}%)", color = data.color)
            }
        }
    }
}

@Composable
fun ManualPieChart(data: List<PieChartData>, modifier: Modifier = Modifier, strokeWidth: Float = 60f) {
    var startAngle = -90f
    Canvas(modifier = modifier) {
        data.forEach { slice ->
            val sweepAngle = slice.value * 360f
            drawArc(color = slice.color, startAngle = startAngle, sweepAngle = sweepAngle, useCenter = false, style = Stroke(width = strokeWidth, cap = StrokeCap.Butt))
            startAngle += sweepAngle
        }
    }
}

@Composable
fun CategoryLegendItem(text: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 4.dp)
    ) {
        Box(modifier = Modifier.size(16.dp).background(color, shape = MaterialTheme.shapes.small))
        Spacer(Modifier.width(16.dp))
        Text(text, fontSize = 16.sp)
    }
}

// The CustomCalendarReport function has been moved out and placed here.
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalendarReport(calendarData: Map<LocalDate, CalendarDayData>) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = currentMonth.atDay(1)
    val firstDayOfWeekOffset = (firstDayOfMonth.dayOfWeek.value - 1)

    val daysOfWeek = listOf("M", "T", "W", "T", "F", "S", "S")

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                Icon(Icons.Default.ChevronLeft, "Previous Month")
            }
            Text(
                text = "${
                    currentMonth.month.getDisplayName(
                        TextStyle.FULL,
                        Locale.getDefault()
                    )
                } ${currentMonth.year}",
                style = MaterialTheme.typography.headlineSmall,
            )
            IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                Icon(Icons.Default.ChevronRight, "Next Month")
            }
        }
        Spacer(Modifier.height(16.dp))


        Row(modifier = Modifier.fillMaxWidth()) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            content = {
                items(firstDayOfWeekOffset) { }
                items(daysInMonth) { dayIndex ->
                    val day = dayIndex + 1
                    val date = currentMonth.atDay(day)
                    val dayData = calendarData[date]

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(
                                if (date == selectedDate) MaterialTheme.colorScheme.primary else Color.Transparent
                            )
                            .clickable {
                                selectedDate = if (selectedDate == date) null else date
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            val textColor =
                                if (date == selectedDate) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                            Text(text = "$day", color = textColor, fontSize = 14.sp)

                            if (dayData != null) {
                                Text(
                                    text = String.format("$%.0f", dayData.totalAmount),
                                    color = if (date == selectedDate) MaterialTheme.colorScheme.onPrimary.copy(
                                        alpha = 0.8f
                                    ) else ChartRed,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}
