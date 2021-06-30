package android_courses.newsapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android_courses.newsapp.NetworkConnection
import android_courses.newsapp.presentation.viewmodel.NewsViewModel
import android_courses.newsapp.presentation.NewsViewModelProviderFactory
import android_courses.newsapp.R
import android_courses.newsapp.Utill.isVisible
import android_courses.newsapp.base.BaseActivity
import android_courses.newsapp.data.db.repository.NewsRepository
import android_courses.newsapp.presentation.MainActivity.Companion.isConnect
import android_courses.newsapp.presentation.adapter.NewsAdapter
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment(R.layout.fragment_news) {
    private lateinit var buttonSelection: AppCompatImageButton
    private lateinit var buttonSettings: AppCompatImageButton
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var viewModel: NewsViewModel
    private lateinit var networkConnection: NetworkConnection
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter ({
            Log.d("ClickOnArticle",
                    it.description) },
                {
                    Snackbar.make(requireView(),it, Snackbar.LENGTH_SHORT).show()
                })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newsRepository = NewsRepository()
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        networkConnection = NetworkConnection((context as BaseActivity).application)
        networkConnection.observe(this, { isConnected ->
            isConnect = if (isConnected) 1 else 0
            if (isConnected) {
                val keyWord: String? =
                    SelectionFragment.sharedPreferences?.getString(SelectionFragment.KEY_WORD, "error")

                if (keyWord == null) {
                    viewModel.getBreakingNews("us")
                } else {
                    viewModel.getNewsByKeyWord(keyWord)
                }
            } else {
                Toast.makeText(
                    context,
                    "No Internet Connection. Please check your internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.breakingNewsListLiveData.observe(viewLifecycleOwner, Observer { response ->
            newsAdapter.listOfItems = response
            newsAdapter.notifyDataSetChanged()
        })

        viewModel.errorStateLiveData.observe(viewLifecycleOwner, Observer { error ->
            Log.e("TAG", "An error: $error")
        })

        viewModel.loadingMutableLiveData.observe(viewLifecycleOwner, Observer { visibility ->
            pagination_progress_bar.isVisible(visibility)
        })

        buttonSelection = view.findViewById(R.id.img_settingsLine)
        buttonSettings = view.findViewById(R.id.img_settings)
        buttonSelection.setOnClickListener {
            (requireActivity() as BaseActivity).fragmentRouter.openSelectionFragment()
        }
        buttonSettings.setOnClickListener {
            (requireActivity() as BaseActivity).fragmentRouter.openSettingsFragment()
        }
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        swipeRefresh.setOnRefreshListener {
            if (isConnect == 1) {
                SelectionFragment.sharedPreferences?.edit()?.clear()?.apply()
                viewModel.getBreakingNews("us")
            }
            swipeRefresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        when(isConnect) {
            0 -> {Toast.makeText(
                context,
                "No Internet Connection. Please check your internet connection",
                Toast.LENGTH_SHORT
            ).show()}
            1 -> {
                val keyWord: String? =
                    SelectionFragment.sharedPreferences?.getString(SelectionFragment.KEY_WORD, "error")

                if (keyWord == null) {
                    viewModel.getBreakingNews("us")
                } else {
                    viewModel.getNewsByKeyWord(keyWord)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        rv_breaking_news.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}