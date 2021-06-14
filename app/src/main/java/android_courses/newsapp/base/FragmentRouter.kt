package android_courses.newsapp.base

import android_courses.newsapp.SignInFragment
import android_courses.newsapp.SignUpFragment
import android_courses.newsapp.fragments.NewsFragment
import android_courses.newsapp.fragments.SelectionFragment
import android_courses.newsapp.splash.SplashScreenFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentRouter(private val containerId: Int, private val fragmentManager: FragmentManager) :
    Router {

    private fun openFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().replace(containerId, fragment).commit()
    }

    override fun openLogInFragment() {
        openFragment(SignInFragment())
    }

    override fun openSignUpFragment() {
        openFragment(SignUpFragment())
    }
    override fun openSplashFragment() {
        openFragment(SplashScreenFragment())
    }

    override fun openNewsFragment() {
        openFragment(NewsFragment())
    }
}