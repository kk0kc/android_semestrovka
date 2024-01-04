package itis.lisa.semestrovka.basicfeature.presentation

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import itis.lisa.semestrovka.basicfeature.presentation.model.NewsDisplayable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class NewsUiState(
    val isLoading: Boolean = false,
    val news: List<NewsDisplayable> = emptyList(),
    val isError: Boolean = false,
) : Parcelable {

    sealed class PartialState {
        data object Loading : PartialState()

        data class Fetched(val list: List<NewsDisplayable>) : PartialState()

        data class Error(val throwable: Throwable) : PartialState()
    }
}
