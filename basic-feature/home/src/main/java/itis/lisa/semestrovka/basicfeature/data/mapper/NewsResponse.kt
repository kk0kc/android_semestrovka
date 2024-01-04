package itis.lisa.semestrovka.basicfeature.data.mapper

import itis.lisa.semestrovka.basicfeature.data.local.model.NewsCached
import itis.lisa.semestrovka.basicfeature.data.remote.model.NewsResponse
import itis.lisa.semestrovka.basicfeature.domain.model.News

fun NewsResponse.toDomainModel() = News(
    id = id,
    title = title,
    description = description,
    url = url,
    author = author,
    image = image,
    language = language,
    category = category.random(),
    published = published
)

fun NewsCached.toDomainModel() = News(
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

fun News.toEntityModel() = NewsCached(
    id = id,
    title = title,
    description = description,
    url = url,
    author = author,
    image = image,
    language = language,
    category = category,
    published = published,
)
