package itis.lisa.semestrovka.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationDestination(
    val route: String,
    val icon: ImageVector?,
    var title: String
) {
    data object News : NavigationDestination("newsDestination", Icons.Rounded.Home, "Home")
    data object History : NavigationDestination("historyDestination", Icons.Rounded.List, "History")
    data object Profile : NavigationDestination("profileDestination", Icons.Rounded.Info, "Profile")
    data object Settings : NavigationDestination("settingsDestination", Icons.Rounded.Settings, "Settings")
    data object Back : NavigationDestination("navigationBack",Icons.Rounded.ArrowBack,"Navigate")
}
