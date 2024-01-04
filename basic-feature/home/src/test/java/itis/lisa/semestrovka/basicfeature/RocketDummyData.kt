package itis.lisa.semestrovka.basicfeature

import itis.lisa.semestrovka.basicfeature.data.remote.model.NewsResponse
import itis.lisa.semestrovka.basicfeature.domain.model.News

internal fun generateTestNewsFromRemote() = NewsResponse(
    id = "1",
    title = "test news",
    author = "pravda",
    category = listOf("sport"),
    url = "https://www.bbc.com/news/topics/cvjk7vv0w40t",
    image = "https://hips.hearstapps.com/hmg-prod/images/cat-instagram-captions-64ff2dfa47e9a.jpg",
)

internal fun generateTestNewsFromDomain() = News(
    id = "1",
    title = "test news",
    author = "pravda",
    category = "sport",
    description = "wooow",
    image = "https://hips.hearstapps.com/hmg-prod/images/cat-instagram-captions-64ff2dfa47e9a.jpg",
    language = "ru",
    published = "3.01.2024",
    url = "https://www.bbc.com/news/topics/cvjk7vv0w40t"
)
