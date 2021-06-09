package android_courses.newsapp.repository
import android_courses.newsapp.model.NewsResponse
import android_courses.newsapp.network.RetrofitInstance
import retrofit2.Response

class NewsRepository : Repository {
    override suspend fun getBreakingNews(countryCode: String): Response<NewsResponse> {
        return RetrofitInstance.api.getNews(countryCode)
    }
}