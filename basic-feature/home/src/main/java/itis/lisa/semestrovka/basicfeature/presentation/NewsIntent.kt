package itis.lisa.semestrovka.basicfeature.presentation

sealed class NewsIntent {
    data object RefreshNews : NewsIntent()
    data class NewsClicked(val uri: String) : NewsIntent()
}
