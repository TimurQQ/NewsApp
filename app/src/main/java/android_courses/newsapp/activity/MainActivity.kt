package android_courses.newsapp.activity



import android.os.Bundle
import android_courses.newsapp.R
import android_courses.newsapp.base.BaseActivity

class MainActivity : BaseActivity(R.id.container)  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fragmentRouter.openSplashFragment()
    }
    override fun onBackPressed() {
        when (supportFragmentManager.backStackEntryCount) {
            0 -> super.onBackPressed()
            else -> supportFragmentManager.popBackStack()
        }
    }

    // restart application on click ON OFF
   /* private fun restartApplication(){
        val i = Intent(applicationContext,MainActivity::class.java)
        startActivity(i)
        finish()
    }*/
}




