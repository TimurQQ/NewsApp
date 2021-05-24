package android_courses.newsapp.repository

import android_courses.newsapp.network.RetrofitInstance

class NewsRepository {
    suspend fun getBreakingNews(countryCode: String) =
        RetrofitInstance.api.getNews(countryCode)
}