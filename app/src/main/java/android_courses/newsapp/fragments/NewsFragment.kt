package android_courses.newsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android_courses.newsapp.NewsViewModel
import android_courses.newsapp.NewsViewModelProviderFactory
import android_courses.newsapp.R
import android_courses.newsapp.Utill.isVisible
import android_courses.newsapp.adapter.NewsAdapter
import android_courses.newsapp.repository.NewsRepository
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment(R.layout.fragment_news) {
    lateinit var viewModel: NewsViewModel
    val newsAdapter: NewsAdapter by lazy {
        NewsAdapter {
            Log.d("ClickOnArticle",
                "${it.description}") } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsRepository = NewsRepository()
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        setupRecyclerView()

        viewModel.breakingNewsListLiveData.observe(viewLifecycleOwner, Observer { response ->
            newsAdapter.differ.submitList(response.articles)
        })

        viewModel.errorStateLiveData.observe(viewLifecycleOwner, Observer { error ->
            Log.e("TAG", "An error: $error")})

        viewModel.loadingMutableLiveData.observe(viewLifecycleOwner, Observer { visibility ->
            pagination_progress_bar.isVisible(visibility)
        })
    }

    private fun setupRecyclerView() {
        rv_breaking_news.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}

