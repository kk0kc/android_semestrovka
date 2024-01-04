package itis.lisa.semestrovka.basicfeature.presentation

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import itis.lisa.semestrovka.basicfeature.domain.usecase.GetNewsUseCase
import itis.lisa.semestrovka.basicfeature.domain.usecase.RefreshNewsUseCase
import itis.lisa.semestrovka.basicfeature.presentation.NewsEvent.OpenWebBrowserWithDetails
import itis.lisa.semestrovka.basicfeature.presentation.NewsIntent.RefreshNews
import itis.lisa.semestrovka.basicfeature.presentation.NewsIntent.NewsClicked
import itis.lisa.semestrovka.basicfeature.presentation.NewsUiState.PartialState
import itis.lisa.semestrovka.basicfeature.presentation.NewsUiState.PartialState.Error
import itis.lisa.semestrovka.basicfeature.presentation.NewsUiState.PartialState.Fetched
import itis.lisa.semestrovka.basicfeature.presentation.NewsUiState.PartialState.Loading
import itis.lisa.semestrovka.basicfeature.presentation.mapper.toPresentationModel
import itis.lisa.semestrovka.core.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

private const val HTTP_PREFIX = "http"
private const val HTTPS_PREFIX = "https"

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val refreshNewsUseCase: RefreshNewsUseCase,
    savedStateHandle: SavedStateHandle,
    newsInitialState: NewsUiState,
) : BaseViewModel<NewsUiState, PartialState, NewsEvent, NewsIntent>(
    savedStateHandle,
    newsInitialState,
) {
    init {
        observeNews()
    }

    override fun mapIntents(intent: NewsIntent): Flow<PartialState> = when (intent) {
        is RefreshNews -> refreshNews()
        is NewsClicked -> newsClicked(intent.uri)
    }

    override fun reduceUiState(
        previousState: NewsUiState,
        partialState: PartialState,
    ): NewsUiState = when (partialState) {
        is Loading -> previousState.copy(
            isLoading = true,
            isError = false,
        )
        is Fetched -> previousState.copy(
            isLoading = false,
            news = partialState.list,
            isError = false,
        )
        is Error -> previousState.copy(
            isLoading = false,
            isError = true,
        )
    }

    private fun observeNews() = acceptChanges(
        getNewsUseCase()
            .map { result ->
                result.fold(
                    onSuccess = { newsList ->
                        Fetched(newsList.map { it.toPresentationModel() })
                    },
                    onFailure = {
                        Error(it)
                    },
                )
            }
            .onStart {
                emit(Loading)
            },
    )

    private fun refreshNews(): Flow<PartialState> = flow {
        refreshNewsUseCase()
            .onFailure {
                emit(Error(it))
            }
    }

    private fun newsClicked(uri: String): Flow<PartialState> {
        if (uri.startsWith(HTTP_PREFIX) || uri.startsWith(HTTPS_PREFIX)) {
            publishEvent(OpenWebBrowserWithDetails(uri))
        }

        return emptyFlow()
    }
}
