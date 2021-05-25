package android_courses.newsapp.network

import android_courses.newsapp.Constants.Companion.API_KEY
import android_courses.newsapp.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("v2/top-headlines")
    suspend fun getNews(
            @Query("country")
            countryCode: String = "ru",
            @Query("page")
            pageNumber: Int = 1,
            @Query("apiKey")
            apiKey: String = API_KEY
    ): Response<NewsResponse>
}