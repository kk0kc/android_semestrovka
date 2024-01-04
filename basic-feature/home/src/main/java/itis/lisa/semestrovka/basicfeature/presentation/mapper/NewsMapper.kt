package itis.lisa.semestrovka.basicfeature.presentation.mapper

import itis.lisa.semestrovka.basicfeature.domain.model.News
import itis.lisa.semestrovka.basicfeature.presentation.model.NewsDisplayable

private const val TONNE = 1_000
private const val MILLION = 1_000_000

fun News.toPresentationModel() = NewsDisplayable(
    id = id,
    title = title,
    description = description,
    url = url,
    author = author,
    image = image,
    language = language,
    category = category,
    published = published
)
