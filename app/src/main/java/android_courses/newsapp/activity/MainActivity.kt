package android_courses.newsapp.activity

import android.os.Bundle
import android_courses.newsapp.R
import android_courses.newsapp.base.BaseActivity
import android.widget.Toast
import android_courses.newsapp.*
import androidx.lifecycle.Observer

class MainActivity : BaseActivity(R.id.container){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentRouter.openSplashFragment()
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected) {
//                Toast.makeText(this@MainActivity, "Соединение есть", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "No Internet Connection. Please check your internet connection", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        when(supportFragmentManager.backStackEntryCount) {
            0 -> super.onBackPressed()
            else -> supportFragmentManager.popBackStack()
        }
    }
}