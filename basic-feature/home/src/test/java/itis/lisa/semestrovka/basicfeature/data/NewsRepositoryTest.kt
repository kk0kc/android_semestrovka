package itis.lisa.semestrovka.basicfeature.data

import itis.lisa.semestrovka.basicfeature.data.local.dao.NewsDao
import itis.lisa.semestrovka.basicfeature.data.mapper.toDomainModel
import itis.lisa.semestrovka.basicfeature.data.mapper.toEntityModel
import itis.lisa.semestrovka.basicfeature.data.remote.api.NewsApi
import itis.lisa.semestrovka.basicfeature.data.repository.NewsRepositoryImpl
import itis.lisa.semestrovka.basicfeature.domain.repository.NewsRepository
import itis.lisa.semestrovka.basicfeature.generateTestNewsFromRemote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NewsRepositoryTest {

    @RelaxedMockK
    private lateinit var newsApi: NewsApi

    @RelaxedMockK
    private lateinit var newsDao: NewsDao

    private lateinit var objectUnderTest: NewsRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        setUpNewsRepository()
    }

    @Test
    fun `should refresh news if local database is empty`() = runTest {
        // Given
        every { newsDao.getNews() } returns flowOf(emptyList())

        // When
        objectUnderTest.getNews().collect()

        // Then
        coVerifyOrder {
            newsApi.getNews()
            newsDao.saveNews(any())
        }
    }

    @Test
    fun `should save mapped news locally if retrieved from remote`() = runTest {
        // Given
        val testNewsFromRemote = listOf(generateTestNewsFromRemote())
        val testNewsToCache = testNewsFromRemote.map { it.toDomainModel().toEntityModel() }
        coEvery { newsApi.getNews().news } returns testNewsFromRemote

        // When
        objectUnderTest.refreshNews()

        // Then
        coVerify { newsDao.saveNews(testNewsToCache) }
    }

    private fun setUpNewsRepository() {
        objectUnderTest = NewsRepositoryImpl(
            newsApi,
            newsDao,
        )
    }
}
