package app.futured.academyproject

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val icon: ImageVector
) {
    object Home: BottomBarScreen(
        route = "home",
        icon = Icons.Default.Home
    )
    object Favorite: BottomBarScreen(
        route = "favorite",
        icon = Icons.Default.Favorite
    )
}
