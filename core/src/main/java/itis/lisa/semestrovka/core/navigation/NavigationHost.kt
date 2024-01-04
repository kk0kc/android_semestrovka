package itis.lisa.semestrovka.core.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun NavigationHost(
    navController: NavHostController,
    factories: Set<NavigationFactory>,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.News.route,
        modifier = modifier,
    ) {
        composable(NavigationDestination.History.route) {
            CenterText(text = "History", modifier = Modifier.fillMaxSize())
        }
        composable(NavigationDestination.Profile.route) {
            CenterText(text = "Profile", modifier = Modifier.fillMaxSize())
        }
        composable(NavigationDestination.Settings.route) {
            CenterText(text = "Settings", modifier = Modifier.fillMaxSize())
        }
        factories.forEach {
            it.create(this)
        }
    }
}

@Composable
fun CenterText(text: String, modifier: Modifier = Modifier) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, fontSize = 32.sp)
    }
}
