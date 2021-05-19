package android_courses.newsapp

import android.content.Context
import java.io.IOException

class FileManager {
    private var callback: Callback? = null

    interface Callback {
        fun callingBack(s: String?)
    }

    fun registerCallBack(callback: Callback?) {
        this.callback = callback
    }

    fun setDataToFile(value: String, context: Context) {
        try {
            val fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            fos.write(value.toByteArray())
            callback!!.callingBack("Файл сохранен")
        } catch (e: IOException) {
            callback!!.callingBack(e.message)
        }
    }

    fun getDataFromFile(context: Context) {
        try {
            val fin = context.openFileInput(FILE_NAME)
            val bytes = ByteArray(fin.available())
            fin.read(bytes)
            val text = String(bytes)
            callback!!.callingBack(text)
        } catch (e: IOException) {
        }
    }

    companion object {
        private const val FILE_NAME = "HELLO_WORLD.txt"
    }
}