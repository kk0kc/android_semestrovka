package itis.lisa.semestrovka.basicfeature.domain

import app.cash.turbine.test
import itis.lisa.semestrovka.basicfeature.domain.repository.NewsRepository
import itis.lisa.semestrovka.basicfeature.domain.usecase.GetNewsUseCase
import itis.lisa.semestrovka.basicfeature.domain.usecase.getNews
import itis.lisa.semestrovka.basicfeature.generateTestNewsFromDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertEquals

class GetNewsUseCaseTest {

    @RelaxedMockK
    private lateinit var newsRepository: NewsRepository

    private lateinit var objectUnderTest: GetNewsUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        setUpGetNewsUseCase()
    }

    @Test
    fun `should wrap result with success if repository doesn't throw`() = runTest {
        // Given
        val testNewsFromDomain = listOf(generateTestNewsFromDomain())
        coEvery { newsRepository.getNews() } returns flowOf(testNewsFromDomain)

        // When-Then
        objectUnderTest.invoke().test {
            val result = awaitItem()

            assertEquals(
                expected = Result.success(testNewsFromDomain),
                actual = result,
            )
            awaitComplete()
        }
    }

    @Test
    fun `should retry operation if repository throws IOException`() = runTest {
        // Given
        val testException = IOException("Test message")
        val testNewsFromDomain = listOf(generateTestNewsFromDomain())
        coEvery { newsRepository.getNews() } throws testException andThen flowOf(testNewsFromDomain)

        // When-Then
        assertThrows<IOException> {
            objectUnderTest.invoke().test {
                val errorResult = awaitItem()

                assertEquals(
                    expected = Result.failure(testException),
                    actual = errorResult,
                )

                val itemsResult = awaitItem()

                assertEquals(
                    expected = Result.success(testNewsFromDomain),
                    actual = itemsResult,
                )
            }
        }
    }

    @Test
    fun `should rethrow if repository throws CancellationException`() = runTest {
        // Given
        coEvery { newsRepository.getNews() } throws CancellationException()

        // When-Then
        assertThrows<CancellationException> {
            objectUnderTest.invoke()
        }
    }

    @Test
    fun `should wrap result with failure if repository throws other Exception`() = runTest {
        // Given
        val testException = Exception("Test message")
        coEvery { newsRepository.getNews() } throws testException

        // When-Then
        assertThrows<Exception> {
            objectUnderTest.invoke().test {
                val result = awaitItem()

                assertEquals(
                    expected = Result.failure(testException),
                    actual = result,
                )
            }
        }
    }

    private fun setUpGetNewsUseCase() {
        objectUnderTest = GetNewsUseCase {
            getNews(newsRepository)
        }
    }
}
