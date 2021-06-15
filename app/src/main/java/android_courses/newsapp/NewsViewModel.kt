package android_courses.newsapp

import android_courses.newsapp.fragments.SelectionFragment
import android_courses.newsapp.model.NewsResponse
import android_courses.newsapp.network.RetrofitInstance
import android_courses.newsapp.repository.NewsRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

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

        loadingMutableLiveData.postValue(false)
        val body = response.body()
        if (!response.isSuccessful) {
            errorStateLiveData.postValue("Error")
            return@launch
        }
        breakingNewsListLiveData.postValue(body)
    }

    fun getNewsByKeyWord(keyWord: String) = viewModelScope.launch {
        loadingMutableLiveData.postValue(true)
        val response = newsRepository.getNewsByKeyWord(keyWord)

        loadingMutableLiveData.postValue(false)
        val body = response.body()
        if (!response.isSuccessful) {
            errorStateLiveData.postValue("Error")
            return@launch
        }
        breakingNewsListLiveData.postValue(body)
    }
}
