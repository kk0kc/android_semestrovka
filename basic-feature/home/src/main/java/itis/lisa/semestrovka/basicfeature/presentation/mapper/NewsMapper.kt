package itis.lisa.semestrovka.basicfeature.presentation.mapper

import itis.lisa.semestrovka.basicfeature.domain.model.News
import itis.lisa.semestrovka.basicfeature.presentation.model.NewsDisplayable

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
