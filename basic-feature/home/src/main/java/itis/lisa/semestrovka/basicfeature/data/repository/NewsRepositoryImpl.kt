package itis.lisa.semestrovka.basicfeature.data.repository

import itis.lisa.semestrovka.basicfeature.data.local.dao.NewsDao
import itis.lisa.semestrovka.basicfeature.data.mapper.toDomainModel
import itis.lisa.semestrovka.basicfeature.data.mapper.toEntityModel
import itis.lisa.semestrovka.basicfeature.data.remote.api.NewsApi
import itis.lisa.semestrovka.basicfeature.domain.model.News
import itis.lisa.semestrovka.basicfeature.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao,
) : NewsRepository {

    override fun getNews(): Flow<List<News>> {
        return newsDao
            .getNews()
            .map { newsCached ->
                newsCached.map { it.toDomainModel() }
            }
            .onEach { news ->
                if (news.isEmpty()) {
                    refreshNews()
                }
            }
    }

    override suspend fun refreshNews() {
        newsApi
            .getNews().news
            .map {
                it.toDomainModel().toEntityModel()
            }
            .also {
                newsDao.saveNews(it)
            }
    }
}
