package android_courses.newsapp.Utill

import android.content.Context
import android.content.SharedPreferences

class SaveData(context:Context){

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("file", Context.MODE_PRIVATE)

    //This method will save the night Mode state:TRUE or FALSE
    fun setLightModeState(state: Boolean?) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("Light",state!!)
        editor.apply()
    }

    //This method will load the night mode state
    fun loadLightModeState(): Boolean {
        val state: Boolean = sharedPreferences.getBoolean("Light",false)
        return (state)
    }
}