package ca.gbc.smartpocketprototype.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.gbc.smartpocketprototype.data.ExpenseRepository
import ca.gbc.smartpocketprototype.ui.theme.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

data class PieChartData(
    val category: String,
    val value: Float,
    val color: Color
)

data class CalendarDayData(
    val date: LocalDate,
    val totalAmount: Double
)

data class ReportsUiState(
    val pieChartData: List<PieChartData> = emptyList(),
    val totalSpending: Double = 0.0,
    val calendarData: Map<LocalDate, CalendarDayData> = emptyMap()
)

class ReportsViewModel(repository: ExpenseRepository) : ViewModel() {

    private val categoryColors = listOf(
        ChartRed, ChartYellow, md_theme_light_primary, ChartPurple, ChartGreen, md_theme_light_onSurface
    )
    @RequiresApi(Build.VERSION_CODES.O)
    val uiState: StateFlow<ReportsUiState> = repository.transactions
        .combine(repository.userPreferences) { transactions, _ ->
            val total = transactions.sumOf { it.amount }

            val pieData = if (total > 0.0) {
                transactions.groupBy { it.category }
                    .mapValues { (_, trans) -> trans.sumOf { it.amount } }
                    .toList()
                    .sortedByDescending { it.second }
                    .mapIndexed { index, (category, amount) ->
                        PieChartData(
                            category = category,
                            value = (amount / total).toFloat(),
                            color = categoryColors[index % categoryColors.size]
                        )
                    }
            } else {
                emptyList()
            }


            val calendarSpending = transactions
                .groupBy {

                    Instant.ofEpochMilli(it.timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
                }
                .mapValues { (date, dailyTransactions) ->

                    CalendarDayData(
                        date = date,
                        totalAmount = dailyTransactions.sumOf { it.amount }
                    )
                }

            ReportsUiState(
                pieChartData = pieData,
                totalSpending = total,
                calendarData = calendarSpending
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ReportsUiState()
        )
}
