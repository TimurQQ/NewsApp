package android_courses.newsapp.fragments

import android.os.Bundle
import android.view.View
import android_courses.newsapp.MainActivity
import android_courses.newsapp.NewsViewModel
import androidx.fragment.app.Fragment
import android_courses.newsapp.R
import android_courses.newsapp.adapter.NewsAdapter

class NewsFragment : Fragment(R.layout.fragment_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel






    }
}
