package android_courses.newsapp

import android_courses.newsapp.Utill.Resource
import android_courses.newsapp.model.NewsResponse
import android_courses.newsapp.network.NewsAPI
import android_courses.newsapp.repository.NewsRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun breakingNewsResponse(response: Response<NewsResponse>, countryCode: String) {
        if (response.isSuccessful) {
            response.body()?.let {
                val news = newsRepository.getBreakingNews(countryCode)
                breakingNewsListLiveData.postValue(news)
            }
        }
        return errorStateLiveData.postValue("Ошибочка")
    }

}
