package android_courses.newsapp.presentation

import android_courses.newsapp.data.db.repository.NewsRepository
import android_courses.newsapp.presentation.viewmodel.NewsViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsViewModelProviderFactory(val newsRepository: NewsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}