package android_courses.newsapp.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(private var container: Int) : AppCompatActivity(){

    val fragmentRouter: Router by lazy {
        FragmentRouter(
            container,
            supportFragmentManager
        )
    }

}

