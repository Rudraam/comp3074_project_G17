package ca.gbc.smartpocketprototype.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ca.gbc.smartpocketprototype.R

val Playfair = FontFamily(
    Font(R.font.playfair_regular, FontWeight.Normal),
    Font(R.font.playfair_bold, FontWeight.Bold),
    Font(R.font.playfair_italic, FontWeight.Normal),
    Font(R.font.playfair_extrabold, FontWeight.ExtraBold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Playfair,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    headlineMedium = TextStyle(
        fontFamily = Playfair,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontFamily = Playfair,
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold
    ),
    bodyMedium = TextStyle(
        fontFamily = Playfair,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    )
)
