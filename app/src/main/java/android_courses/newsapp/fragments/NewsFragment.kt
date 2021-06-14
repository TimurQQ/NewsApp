package android_courses.newsapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android_courses.newsapp.NewsViewModel
import android_courses.newsapp.NewsViewModelProviderFactory
import android_courses.newsapp.R
import android_courses.newsapp.Utill.Constants.Companion.APP_PREFERENCES
import android_courses.newsapp.Utill.Constants.Companion.IS_REGISTERED
import android_courses.newsapp.Utill.isVisible
import android_courses.newsapp.adapter.NewsAdapter
import android_courses.newsapp.base.BaseActivity
import android_courses.newsapp.repository.NewsRepository
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment(R.layout.fragment_news) {
    lateinit var mAuth: FirebaseAuth
    lateinit var mSettings: SharedPreferences
    lateinit var buttonLogout: AppCompatImageButton
    lateinit var buttonSelection: AppCompatImageButton
    lateinit var viewModel: NewsViewModel
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter {
            Log.d("ClickOnArticle",
                    it.description) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance();
        mSettings = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

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
            Log.e("TAG", "An error: $error")
        })

        viewModel.loadingMutableLiveData.observe(viewLifecycleOwner, Observer { visibility ->
            pagination_progress_bar.isVisible(visibility)
        })

        buttonLogout = view.findViewById(R.id.log_out_btn)
        buttonLogout.setOnClickListener {
            mAuth.signOut()
            with (mSettings.edit()) {
                putBoolean(IS_REGISTERED, false)
                apply()
            }
            (requireActivity() as BaseActivity).fragmentRouter.openSplashFragment()
        }

        buttonSelection = view.findViewById(R.id.img_settingsLine)
        buttonSelection.setOnClickListener {
            (requireActivity() as BaseActivity).fragmentRouter.openSelectionFragment()
        }
    }

    private fun setupRecyclerView() {
        rv_breaking_news.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
