package android_courses.newsapp.data.db.repository

import android_courses.newsapp.domain.entity.NewsResponse
import retrofit2.Response

interface Repository {
    suspend fun getBreakingNews(countryCode : String) : Response<NewsResponse>
    suspend fun getNewsByKeyWord (keyWord : String) : Response<NewsResponse>

}

//TODO work with data room, retrofit, firebase
