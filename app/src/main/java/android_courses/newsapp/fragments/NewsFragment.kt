package android_courses.newsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android_courses.newsapp.MainActivity
import android_courses.newsapp.NewsViewModel
import android_courses.newsapp.R
import android_courses.newsapp.Resource
import android_courses.newsapp.adapter.NewsAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment(R.layout.fragment_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("TAG", "Error: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()

                }
            }
        })
    }

    private fun showProgressBar() {
        pagination_progress_bar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        pagination_progress_bar.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rv_breaking_news.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
