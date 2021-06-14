package android_courses.newsapp.base

import android_courses.newsapp.R
import android_courses.newsapp.SignInFragment
import android_courses.newsapp.SignUpFragment
import android_courses.newsapp.fragments.FragmentSetting
import android_courses.newsapp.fragments.NewsFragment
import android_courses.newsapp.fragments.SelectionFragment
import android_courses.newsapp.splash.SplashScreenFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentRouter(private val containerId: Int, private val fragmentManager: FragmentManager) : Router {

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commit()
    }

    private fun addFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().add(containerId, fragment)
                .addToBackStack(null)
                .commit()
    }
    private fun addFragmentWithAnimation(fragment: Fragment, anim_open: Int = R.animator.slide_in_left,
                                         anim_close: Int = R.animator.slide_in_right) {
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(anim_open, anim_close, anim_open, anim_close)
        transaction.add(containerId, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun openLogInFragment() {
        replaceFragment(SignInFragment())
    }

    override fun openSettingsFragment() {
        addFragmentWithAnimation(FragmentSetting(),
                R.animator.slide_out_right,
                R.animator.slide_out_left)
    }

    override fun openSelectionFragment() {
        addFragmentWithAnimation(SelectionFragment())
    }

    override fun openSignUpFragment() {
        addFragmentWithAnimation(SignUpFragment())
    }
    override fun openSplashFragment() {
        replaceFragment(SplashScreenFragment())
    }

    override fun openNewsFragment() {
        replaceFragment(NewsFragment())
    }
}