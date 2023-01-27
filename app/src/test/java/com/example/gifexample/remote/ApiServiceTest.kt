package com.example.gifexample.remote

import com.example.gifexample.MockData
import com.example.gifexample.model.ResponseGiphyList
import com.example.gifexample.network.OkHttpClientBuilder
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class ApiServiceTest {

    private lateinit var apiService: ApiService
    private var mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClientBuilder.get())
            .build().create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_trendingApi() = runTest {
        try {
            mockWebServer.enqueue(MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(Gson().toJson(MockData.getSearchResponse())))
            var response: ResponseGiphyList?
            launch {
                response = apiService.getTrendingData(10, 0)
                assert(response?.data.isNullOrEmpty().not())
                assert(response?.pagination != null)
                assert(response?.pagination?.count == 1)
                assert(response?.meta != null)
                assert(response?.meta?.msg?.isEmpty() == true)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_searchApi() = runTest {
        try {
            mockWebServer.enqueue(MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(Gson().toJson(MockData.getSearchResponse())))
            var response: ResponseGiphyList?
            launch {
                response = apiService.getSearchData("q", 10, 0)
                assert(response?.data.isNullOrEmpty().not())
                assert(response?.pagination != null)
                assert(response?.pagination?.count == 1)
                assert(response?.meta != null)
                assert(response?.meta?.msg?.isEmpty() == true)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}