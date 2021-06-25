package android_courses.newsapp.presentation

import android.os.Bundle
import android.widget.Toast
import android_courses.newsapp.R
import android_courses.newsapp.NetworkConnection
import android_courses.newsapp.base.BaseActivity

class MainActivity : BaseActivity(R.id.container){
    private lateinit var networkConnection: NetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentRouter.openSplashFragment()
        neworkConnection()
    }

    fun neworkConnection() {
        networkConnection = NetworkConnection(application)
        networkConnection.observe(this, { isConnected ->
            if (isConnected) {
//                Toast.makeText(this, "Соединение есть", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No Internet Connection. Please check your internet connection", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onBackPressed() {
        when (supportFragmentManager.backStackEntryCount) {
            0 -> super.onBackPressed()
            else -> supportFragmentManager.popBackStack()
        }
    }
}