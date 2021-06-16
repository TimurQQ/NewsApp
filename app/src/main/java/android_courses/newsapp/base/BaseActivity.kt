package android_courses.newsapp.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(private var container: Int) : AppCompatActivity(){

    val fragmentRouter: FragmentRouter by lazy {
        FragmentRouter(
            container,
            supportFragmentManager
        )
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
}

