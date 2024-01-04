package itis.lisa.semestrovka.basicfeature.presentation

sealed class NewsEvent {
    data class OpenWebBrowserWithDetails(val uri: String) : NewsEvent()
}
