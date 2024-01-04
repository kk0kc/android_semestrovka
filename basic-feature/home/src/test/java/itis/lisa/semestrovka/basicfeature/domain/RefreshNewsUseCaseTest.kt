package itis.lisa.semestrovka.basicfeature.domain

import itis.lisa.semestrovka.basicfeature.domain.repository.NewsRepository
import itis.lisa.semestrovka.basicfeature.domain.usecase.RefreshNewsUseCase
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import itis.lisa.semestrovka.basicfeature.domain.usecase.refreshNews
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class RefreshNewsUseCaseTest {

    @RelaxedMockK
    private lateinit var newsRepository: NewsRepository

    private lateinit var objectUnderTest: RefreshNewsUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        setUpRefreshNewsUseCase()
    }

    @Test
    fun `should wrap result with success if repository doesn't throw`() = runTest {
        // Given
        coEvery { newsRepository.refreshNews() } just Runs

        // When
        val result = objectUnderTest.invoke()

        // Then
        assertEquals(
            expected = Result.success(Unit),
            actual = result,
        )
    }

    @Test
    fun `should rethrow if repository throws CancellationException`() = runTest {
        // Given
        coEvery { newsRepository.refreshNews() } throws CancellationException()

        // When-Then
        assertThrows<CancellationException> {
            objectUnderTest.invoke()
        }
    }

    @Test
    fun `should wrap result with failure if repository throws other Throwable`() = runTest {
        // Given
        val testException = Throwable("Test message")
        coEvery { newsRepository.refreshNews() } throws testException

        // When-Then
        assertThrows<Throwable> {
            val result = objectUnderTest.invoke()

            assertEquals(
                expected = Result.failure(testException),
                actual = result,
            )
        }
    }

    private fun setUpRefreshNewsUseCase() {
        objectUnderTest = RefreshNewsUseCase {
            refreshNews(newsRepository)
        }
    }
}
