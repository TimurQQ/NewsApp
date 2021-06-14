package android_courses.newsapp.activity

import android.os.Bundle
import android_courses.newsapp.R
import android_courses.newsapp.base.BaseActivity

class MainActivity : BaseActivity(R.id.container){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentRouter.openSplashFragment()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}