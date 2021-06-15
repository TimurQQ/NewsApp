package android_courses.newsapp.data.db.repository

import android_courses.newsapp.data.db.remote.api.RetrofitInstance
import android_courses.newsapp.domain.entity.NewsResponse
import retrofit2.Response

class NewsRepository : Repository {
    override suspend fun getBreakingNews(countryCode: String): Response<NewsResponse> {
        return RetrofitInstance.api.getNews(countryCode)
    }
}