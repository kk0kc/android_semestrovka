package itis.lisa.semestrovka.basicfeature.domain.repository

import itis.lisa.semestrovka.basicfeature.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<List<News>>
    suspend fun refreshNews()
}
