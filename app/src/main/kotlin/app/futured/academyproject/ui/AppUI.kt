package app.futured.academyproject.ui

import androidx.compose.runtime.Composable
import app.futured.academyproject.BottomBarUI
import app.futured.academyproject.ui.theme.AppTheme

@Composable
fun AppUI() {
    AppTheme {
        NavGraph()
        BottomBarUI()
    }
}
