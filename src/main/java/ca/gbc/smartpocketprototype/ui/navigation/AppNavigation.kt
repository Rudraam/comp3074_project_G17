
package ca.gbc.smartpocketprototype.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import ca.gbc.smartpocketprototype.data.ExpenseRepository
import ca.gbc.smartpocketprototype.ui.screens.*
import ca.gbc.smartpocketprototype.viewmodels.*

object AppRoutes {
    const val HOME = "home"
    const val REPORTS = "reports"
    const val ADD_EXPENSE = "add_expense"
    const val SETTINGS = "settings"
    const val ALL_TRANSACTIONS = "all_transactions"
}

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModelFactory = ViewModelFactory(ExpenseRepository.getInstance())
    val homeViewModel: HomeViewModel = viewModel(factory = viewModelFactory)
    val settingsViewModel: SettingsViewModel = viewModel(factory = viewModelFactory)
    val addExpenseViewModel: AddExpenseViewModel = viewModel(factory = viewModelFactory)
    val reportsViewModel: ReportsViewModel = viewModel(factory = viewModelFactory)
    val allTransactionsViewModel: AllTransactionsViewModel = viewModel(factory = viewModelFactory)

    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Default.Home, AppRoutes.HOME),
        BottomNavItem("Reports", Icons.Default.Assessment, AppRoutes.REPORTS),
        BottomNavItem("Settings", Icons.Default.Settings, AppRoutes.SETTINGS)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppRoutes.HOME) { HomeScreen(navController = navController, viewModel = homeViewModel) }
            composable(AppRoutes.REPORTS) { ReportsScreen(viewModel = reportsViewModel) }
            composable(AppRoutes.SETTINGS) { SettingsScreen(navController = navController, viewModel = settingsViewModel) }
            composable(AppRoutes.ADD_EXPENSE) {
                AddExpenseScreen(navController = navController, viewModel = addExpenseViewModel)
            }
            composable(AppRoutes.ALL_TRANSACTIONS) {
                AllTransactionsScreen(navController = navController, viewModel = allTransactionsViewModel)
            }
        }
    }
}
