package android_courses.newsapp

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask


class MainActivity : AppCompatActivity() {

    private var dataString = " "
    private val saveFileManager = FileManager()
    private val loadFileManager = FileManager()
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // получаем экземпляр FragmentTransaction
		val fragmentManager : FragmentManager = supportFragmentManager
		val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()

		// добавляем фрагмент
		val splashScreenFragment = SplashScreenFragment()
		fragmentTransaction.add(R.id.container, splashScreenFragment)
		fragmentTransaction.commit()

        saveFileManager.registerCallBack(object : FileManager.Callback {
            override fun callingBack(s: String?) {
                val toast = Toast.makeText(baseContext, s, Toast.LENGTH_LONG)
                toast.show()
            }
        })
        loadFileManager.registerCallBack(object : FileManager.Callback {
            override fun callingBack(s: String?) {
                //TODO:: Загружаем данные на сервер

                val data = s!!.toByteArray()
                val textRef = storageRef.child("/HELLO_WORLD.txt")

                val uploadTask: UploadTask = textRef.putBytes(data)
                uploadTask.addOnFailureListener { }.addOnSuccessListener { }
            }
        })

        val editText = findViewById<EditText>(R.id.editTextTextPersonName)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                dataString = "\t" + s
            }
            override fun afterTextChanged(s: Editable) {}
        })

        val saveClick = View.OnClickListener { saveFileManager.setDataToFile(dataString, baseContext) }
        val saveBtn = findViewById<Button>(R.id.save_button)
        saveBtn.setOnClickListener(saveClick)

        val loadClick = View.OnClickListener { loadFileManager.getDataFromFile(baseContext)}
        val loadBtn = findViewById<Button>(R.id.load_button)
        loadBtn.setOnClickListener(loadClick)
    }

    fun moveToNext() {
        Log.d("SPLASH_SCREEN", "переходим на LoginFragment")
        //TODO: Переходим на LoginFragment
    }
}