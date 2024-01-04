package itis.lisa.semestrovka.basicfeature.domain.model

import java.time.LocalDate

data class News(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val author: String,
    val image: String,
    val language: String,
    val category: String,
    val published: String,
)
