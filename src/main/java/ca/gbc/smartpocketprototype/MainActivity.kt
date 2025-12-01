package ca.gbc.smartpocketprototype

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ca.gbc.smartpocketprototype.ui.navigation.AppNavigation
import ca.gbc.smartpocketprototype.ui.theme.SmartPocketPrototypeTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartPocketPrototypeTheme {
                Surface(
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
