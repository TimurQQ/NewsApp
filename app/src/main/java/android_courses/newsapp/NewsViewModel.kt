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

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val breakingNewsListLiveData: MutableLiveData<NewsResponse> = MutableLiveData()
    val errorStateLiveData: MutableLiveData<String> = MutableLiveData()
    val loadingMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        loadingMutableLiveData.postValue(true)
        val response = newsRepository.getBreakingNews(countryCode)

        //breakingNewsListLiveData.postValue(breakingNewsResponse(response))

//        when(response){
//            is Resource.Success -> {
//              breakingNewsListLiveData.postValue(response.data?.let { news ->
//                  breakingNewsListLiveData.postValue(news)
//              })
//                loadingMutableLiveData.postValue(false)
//            }
//            is Resource.Error<*> -> {
//               errorStateLiveData.postValue("Ошибочка")
//                loadingMutableLiveData.postValue(false)
//            }
//        }
//        breakingNewsListLiveData.postValue(handleBreakingNewsResponse(response))
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
