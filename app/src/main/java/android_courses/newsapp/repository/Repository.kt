package android_courses.newsapp.repository

import android_courses.newsapp.model.NewsResponse
import retrofit2.Response

interface Repository {
    suspend fun getBreakingNews(countryCode : String) : Response<NewsResponse>
}

//TODO work with data room, retrofit, firebase
