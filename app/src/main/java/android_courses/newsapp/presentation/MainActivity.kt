package android_courses.newsapp.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android_courses.newsapp.*
import android_courses.newsapp.NetworkConnection
import android_courses.newsapp.Utill.SaveData
import android_courses.newsapp.base.BaseActivity
import android_courses.newsapp.base.RestartInterface
import androidx.appcompat.widget.SwitchCompat

class MainActivity : BaseActivity(R.id.container), RestartInterface {

    private var switchCompat: SwitchCompat? = null
    private lateinit var saveData: SaveData

    private lateinit var networkConnection: NetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        //Share preference states look
        saveData = SaveData(this)
        if (saveData.loadLightModeState() == true) {
            setTheme(R.style.darkTheme)
        } else
            setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        neworkConnection()

        //Switch for on off
        switchCompat = findViewById(R.id.on_off) as SwitchCompat?
        if (saveData.loadLightModeState() == true) {
            switchCompat?.isChecked = true
        }

        fragmentRouter.openSplashFragment()
    }
    fun neworkConnection() {
        networkConnection = NetworkConnection(application)
        networkConnection.observe(this, { isConnected ->
            if (isConnected) {
               // Toast.makeText(this, "Соединение есть", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "No Internet Connection. Please check your internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
    override fun onBackPressed() {
        when (supportFragmentManager.backStackEntryCount) {
            0 -> super.onBackPressed()
            else -> supportFragmentManager.popBackStack()
        }
    }
    override fun restartApplication() {
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        finish()

    }
}



