package itis.lisa.semestrovka.basicfeature.presentation.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import itis.lisa.semestrovka.basicfeature.R
import itis.lisa.semestrovka.basicfeature.presentation.NewsEvent
import itis.lisa.semestrovka.basicfeature.presentation.NewsEvent.OpenWebBrowserWithDetails
import itis.lisa.semestrovka.basicfeature.presentation.NewsIntent
import itis.lisa.semestrovka.basicfeature.presentation.NewsIntent.RefreshNews
import itis.lisa.semestrovka.basicfeature.presentation.NewsIntent.NewsClicked
import itis.lisa.semestrovka.basicfeature.presentation.NewsUiState
import itis.lisa.semestrovka.basicfeature.presentation.NewsViewModel
import itis.lisa.semestrovka.core.utils.collectWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun NewsRoute(
    viewModel: NewsViewModel = hiltViewModel(),
) {
    HandleEvents(viewModel.event)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NewsScreen(
        uiState = uiState,
        onIntent = viewModel::acceptIntent,
    )
}

@Composable
internal fun NewsScreen(
    uiState: NewsUiState,
    onIntent: (NewsIntent) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = { onIntent(RefreshNews) },
            modifier = Modifier
                .padding(it),
        ) {
            if (uiState.news.isNotEmpty()) {
                NewsAvailableContent(
                    snackbarHostState = snackbarHostState,
                    uiState = uiState,
                    onNewsClick = { onIntent(NewsClicked(it)) },
                )
            } else {
                NewsNotAvailableContent(
                    uiState = uiState,
                )
            }
        }
    }
}

@Composable
private fun HandleEvents(events: Flow<NewsEvent>) {
    val uriHandler = LocalUriHandler.current

    events.collectWithLifecycle {
        when (it) {
            is OpenWebBrowserWithDetails -> {
                uriHandler.openUri(it.uri)
            }
        }
    }
}

@Composable
private fun NewsAvailableContent(
    snackbarHostState: SnackbarHostState,
    uiState: NewsUiState,
    onNewsClick: (String) -> Unit,
) {
    if (uiState.isError) {
        val errorMessage = stringResource(R.string.news_error_refreshing)

        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = errorMessage,
            )
        }
    }

    NewsListContent(
        newsList = uiState.news,
        onNewsClick = onNewsClick,
    )
}

@Composable
private fun NewsNotAvailableContent(uiState: NewsUiState) {
    when {
        uiState.isLoading -> NewsLoadingPlaceholder()
        uiState.isError -> NewsErrorContent()
    }
}
