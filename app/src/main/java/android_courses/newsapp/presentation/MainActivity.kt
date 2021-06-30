package android_courses.newsapp.presentation

import android.os.Bundle
import android_courses.newsapp.*
import android_courses.newsapp.NetworkConnection
import android_courses.newsapp.Utill.SaveData
import android_courses.newsapp.base.BaseActivity
import android_courses.newsapp.base.RestartInterface
import android_courses.newsapp.presentation.fragments.SelectionFragment
import androidx.appcompat.widget.SwitchCompat

class MainActivity : BaseActivity(R.id.container), RestartInterface {
    companion object{
        var isConnect: Int = -1
    }

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

        //Switch for on off
        switchCompat = findViewById(R.id.on_off) as SwitchCompat?
        if (saveData.loadLightModeState() == true) {
            switchCompat?.isChecked = true
        }
        fragmentRouter.openSplashFragment()
    }

    override fun onBackPressed() {
        when (supportFragmentManager.backStackEntryCount) {
            0 -> super.onBackPressed()
            else -> supportFragmentManager.popBackStack()
        }
    }
    override fun restartApplication() {
        SelectionFragment.sharedPreferences?.edit()?.clear()?.apply()
        recreate()
    }
}



