package android_courses.newsapp.data.db.remote.api

import android_courses.newsapp.Utill.Constants
import android_courses.newsapp.Utill.Constants.Companion.API_KEY
import android_courses.newsapp.domain.entity.NewsResponse
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
            apiKey: String = API_KEY,
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getNewsByKeyWord(
            @Query("country")
            countryCode: String = "us",
            @Query("page")
            pageNumber: Int = 1,
            @Query("apiKey")
            apiKey: String = Constants.API_KEY,
            @Query("q")
            keyWord: String = ""
    ): Response<NewsResponse>
}