package android_courses.newsapp

import android.os.Bundle
import android_courses.newsapp.fragments.NewsFragment
import android_courses.newsapp.repository.NewsRepository
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsFragment  = NewsFragment ()
        makeCurrentFragment(newsFragment)
    }
    private fun makeCurrentFragment(fragment: Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, fragment)
                commit()
            }
}