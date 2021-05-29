package android_courses.newsapp.activity

import android.os.Bundle
import android_courses.newsapp.*
import android_courses.newsapp.base.BaseActivity
import android_courses.newsapp.fragments.NewsFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : BaseActivity(R.id.container){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentRouter.openSplashFragment()


        val newsFragment = NewsFragment ()
        val fragmentManager : FragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container, newsFragment)
        fragmentTransaction.commit()
    }
}