package com.example.history
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.history.presentation.HistoryScreen
import itis.lisa.semestrovka.core.navigation.NavigationDestination
import itis.lisa.semestrovka.core.navigation.NavigationFactory
import javax.inject.Inject

class HistoryNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(NavigationDestination.History.route) {
            HistoryScreen()
        }
    }
}
