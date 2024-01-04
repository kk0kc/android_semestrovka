package itis.lisa.semestrovka.basicfeature.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import itis.lisa.semestrovka.basicfeature.presentation.composable.NewsRoute
import itis.lisa.semestrovka.core.navigation.NavigationDestination
import itis.lisa.semestrovka.core.navigation.NavigationFactory
import javax.inject.Inject

class NewsNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(NavigationDestination.News.route) {
            NewsRoute()
        }
    }
}
