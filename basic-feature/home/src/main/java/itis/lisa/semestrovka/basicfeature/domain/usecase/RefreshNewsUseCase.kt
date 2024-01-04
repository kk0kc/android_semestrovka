package itis.lisa.semestrovka.basicfeature.domain.usecase

import itis.lisa.semestrovka.basicfeature.domain.repository.NewsRepository
import itis.lisa.semestrovka.core.utils.resultOf

fun interface RefreshNewsUseCase : suspend () -> Result<Unit>

suspend fun refreshNews(
    newsRepository: NewsRepository,
): Result<Unit> = resultOf {
    newsRepository.refreshNews()
}
