package com.articles.popular.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.articles.popular.model.Article
import com.articles.popular.model.ArticlesUseCase
import com.articles.popular.model.DataState
import com.articles.popular.model.MostViewsApiData
import com.articles.popular.network.ArticlesApi
import com.articles.popular.utilz.MockResponseFileReader
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MostViewedNetworkTest{
    private val mockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8000

    @get:Rule(order = 0)
    val instantTaskExecutorRule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var articlesApi: ArticlesApi

    private var closeable: AutoCloseable? = null

    @Before
    fun setUp() {
        mockWebServer.start(MOCK_WEBSERVER_PORT)
        closeable = MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)

        val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()

        articlesApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(ArticlesApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        // Resets state of the [Dispatchers.Main] to the original main dispatcher.
        // For example, in Android Main thread dispatcher will be set as [Dispatchers.Main].
        Dispatchers.resetMain()
        // Clean up the TestCoroutineDispatcher to make sure no other work is running.
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test get most views api`() {
        mockWebServer.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("get-most-views-articles.json").content))
        }

        runBlocking {
            val messages = ArrayList<String>()
            messages.add("Settings list")

            val actual= articlesApi.getMostViewedArticles("all-sections",7)

            val expected = MostViewsApiData("OK",
                arrayListOf(Article(
                        uri= "nyt://article/0a820161-3038-5e3e-9a14-5939a00daf23",
                        url= "https://www.nytimes.com/2021/11/08/us/tiktok-hand-signal-abduction.html",
                        id= 100000008066487,
                        source= "New York Times",
                        published_date= "2021-11-08",
                        updated= "2021-11-10 09:36:48",
                        section= "U.S.",
                        subsection= "",
                        nytdsection= "u.s.",
                        adx_keywords= "Missing Persons;Rescues;Social Media;Sign Language;Brick, James Herbert;TikTok (ByteDance);Kentucky;Asheville (NC)",
                        byline= "By Daniel Victor and Eduardo Medina",
                        title= "Missing Girl Is Rescued After Using Hand Signal From TikTok",
                        abstract= "The girl flashed the hand signal from a car on a Kentucky interstate, the authorities said. It was created as a way for people to indicate that they are at risk of abuse and need help.",
                ))
            )
            assertEquals(expected, actual)
        }
    }
}