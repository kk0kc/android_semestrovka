package itis.lisa.semestrovka.basicfeature.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import itis.lisa.semestrovka.basicfeature.generateTestNewsFromDomain
import itis.lisa.semestrovka.basicfeature.presentation.NewsEvent.OpenWebBrowserWithDetails
import itis.lisa.semestrovka.basicfeature.presentation.NewsIntent.RefreshNews
import itis.lisa.semestrovka.basicfeature.presentation.mapper.toPresentationModel
import itis.lisa.semestrovka.core.utils.MainDispatcherExtension
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import itis.lisa.semestrovka.basicfeature.domain.usecase.GetNewsUseCase
import itis.lisa.semestrovka.basicfeature.domain.usecase.RefreshNewsUseCase
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NewsViewModelTest {

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()

    @RelaxedMockK
    private lateinit var getNewsUseCase: GetNewsUseCase

    // there is some issue with mocking functional interface with kotlin.Result(Unit)
    private val refreshNewsUseCase: RefreshNewsUseCase = RefreshNewsUseCase {
        Result.failure(IllegalStateException("Test error"))
    }

    @SpyK
    private var savedStateHandle = SavedStateHandle()

    private lateinit var objectUnderTest: NewsViewModel

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should show loading state with no error state first during init news retrieval`() = runTest {
        // Given
        every { getNewsUseCase() } returns emptyFlow()
        setUpNewsViewModel()

        // When
        // init

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertTrue(actualItem.isLoading)
            assertFalse(actualItem.isError)
        }
    }

    @Test
    fun `should show fetched news with no loading & error state during init news retrieval success`() = runTest {
        // Given
        val testNewsFromDomain = listOf(generateTestNewsFromDomain())
        val testNewsToPresentation = testNewsFromDomain.map { it.toPresentationModel() }
        every { getNewsUseCase() } returns flowOf(
            Result.success(testNewsFromDomain),
        )
        setUpNewsViewModel()

        // When
        // init

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertEquals(
                expected = testNewsToPresentation,
                actual = actualItem.news,
            )
            assertFalse(actualItem.isLoading)
            assertFalse(actualItem.isError)
        }
    }

    @Test
    fun `should show error state with no loading state during init news retrieval failure`() = runTest {
        // Given
        every { getNewsUseCase() } returns flowOf(
            Result.failure(IllegalStateException("Test error")),
        )
        setUpNewsViewModel()

        // When
        // init

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertTrue(actualItem.isError)
            assertFalse(actualItem.isLoading)
        }
    }

    @Test
    fun `should show error state with previously fetched news during news refresh failure`() = runTest {
        // Given
        val testNewsFromDomain = listOf(generateTestNewsFromDomain())
        val testNewsToPresentation = testNewsFromDomain.map { it.toPresentationModel() }
        every { getNewsUseCase() } returns flowOf(
            Result.success(testNewsFromDomain),
        )
        setUpNewsViewModel()

        // When
        objectUnderTest.acceptIntent(RefreshNews)

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertTrue(actualItem.isError)
            assertEquals(
                expected = testNewsToPresentation,
                actual = actualItem.news,
            )
        }
    }

    @Test
    fun `should open web browser if link has proper prefix`() = runTest {
        // Given
        val testUri = "https://www.bbc.com/news/topics/cvjk7vv0w40t"
        every { getNewsUseCase() } returns emptyFlow()
        setUpNewsViewModel()

        // When
        objectUnderTest.acceptIntent(NewsIntent.NewsClicked(testUri))

        // Then
        objectUnderTest.event.test {
            assertEquals(
                expected = OpenWebBrowserWithDetails(testUri),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun `should not open web browser if link is incorrect`() = runTest {
        // Given
        val testUri = "incorrectlink.com"
        every { getNewsUseCase() } returns emptyFlow()
        setUpNewsViewModel()

        // When
        objectUnderTest.acceptIntent(NewsIntent.NewsClicked(testUri))

        // Then
        objectUnderTest.event.test {
            expectNoEvents()
        }
    }

    private fun setUpNewsViewModel(
        initialUiState: NewsUiState = NewsUiState(),
    ) {
        objectUnderTest = NewsViewModel(
            getNewsUseCase,
            refreshNewsUseCase,
            savedStateHandle,
            initialUiState,
        )
    }
}
