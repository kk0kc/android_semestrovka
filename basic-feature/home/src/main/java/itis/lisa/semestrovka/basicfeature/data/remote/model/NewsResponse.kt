package itis.lisa.semestrovka.basicfeature.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("status")
    val status: String,
    @SerialName("news")
    val news: List<NewsResponse>
)


@Serializable
data class NewsResponse(
    @SerialName("id")
    val id: String = "",

    @SerialName("title")
    val title: String = "",

    @SerialName("description")
    val description: String = "",

    @SerialName("url")
    val url: String = "",

    @SerialName("author")
    val author: String = "",

    @SerialName("image")
    val image: String = "",

    @SerialName("language")
    val language: String = "",

    @SerialName("category")
    val category: List<String> = emptyList(),

    @SerialName("published")
    val published: String = "",

)
