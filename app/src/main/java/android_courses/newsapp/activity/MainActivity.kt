package android_courses.newsapp.activity


import android.content.Intent
import android.os.Bundle
import android_courses.newsapp.R
import android_courses.newsapp.Utill.SaveData
import android_courses.newsapp.base.BaseActivity
import androidx.appcompat.widget.SwitchCompat

class MainActivity : BaseActivity(R.id.container) {
    private var switchCompat: SwitchCompat? = null
    private lateinit var saveData: SaveData

    override fun onCreate(savedInstanceState: Bundle?) {

        //Share preference states look
        saveData = SaveData(this)
        if (saveData.loadLightModeState() == true) {
            setTheme(R.style.AppTheme)
        } else
            setTheme(R.style.darkTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Switch for on off
                switchCompat=findViewById(R.id.on_off)as SwitchCompat?
        if(saveData.loadLightModeState()==true) {
            switchCompat!!.isChecked = true
        }


        fragmentRouter.openSplashFragment()
    }

    override fun onBackPressed() {
        when (supportFragmentManager.backStackEntryCount) {
            0 -> super.onBackPressed()
            else -> supportFragmentManager.popBackStack()
        }
    }
    // restart application on click ON OFF
    private fun restartApplication(){
        val i = Intent(applicationContext,MainActivity::class.java)
        startActivity(i)
        finish()
    }
}




