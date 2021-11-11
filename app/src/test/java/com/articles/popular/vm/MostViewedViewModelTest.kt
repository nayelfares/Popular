package com.articles.popular.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.articles.popular.model.ArticlesUseCase
import com.articles.popular.model.DataState
import com.articles.popular.model.MostViewsApiData
import com.articles.popular.network.ArticlesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MostViewedViewModelTest{
    @get:Rule(order = 0)
    val instantTaskExecutorRule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    var closeable : AutoCloseable? = null

    @Mock
    lateinit var articlesApi: ArticlesApi
    lateinit var articlesUseCase: ArticlesUseCase
    lateinit var mostViewedViewModel: MostViewedViewModel

    @Mock
    lateinit var mostViewedDataObserver : Observer<DataState<MostViewsApiData>>

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
        articlesUseCase = ArticlesUseCase(articlesApi)
        mostViewedViewModel = MostViewedViewModel(articlesUseCase)
        mostViewedViewModel.articlesState.observeForever(mostViewedDataObserver)
    }

    @Test
    fun `test articles State observer`()  = runBlockingTest {
        Mockito.`when`(articlesApi.getMostViewedArticles("all-sections",7)).thenReturn(null)
        assertNotNull(mostViewedViewModel.getMostViewed("all-sections",7))
        assertTrue(mostViewedViewModel.articlesState.hasObservers())
    }
}