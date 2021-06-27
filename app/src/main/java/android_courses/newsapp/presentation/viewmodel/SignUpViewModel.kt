package android_courses.newsapp.presentation.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns
import android.view.View
import android.widget.*
import android_courses.newsapp.R
import android_courses.newsapp.Utill.Constants
import android_courses.newsapp.Utill.Constants.Companion.IS_REGISTERED
import android_courses.newsapp.Utill.isVisible
import android_courses.newsapp.base.BaseActivity
import android_courses.newsapp.custom.CustomEditTextView
import androidx.appcompat.widget.AppCompatImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class SignUpViewModel(private val context: Context) : View.OnClickListener{
    private val mSettings: SharedPreferences by lazy {
        context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
    }
    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private lateinit var progressBar: ProgressBar
    private lateinit var editTextEmail: CustomEditTextView
    private lateinit var editTextPassword: CustomEditTextView
    private lateinit var buttonSignUp: Button
    private lateinit var textViewLogin: TextView

    fun onViewCreated(view: View) {
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        progressBar = view.findViewById(R.id.progressbar)
        buttonSignUp = view.findViewById(R.id.buttonSignUp)
        textViewLogin = view.findViewById(R.id.textViewLogin)
        buttonSignUp.setOnClickListener(this)
        textViewLogin.setOnClickListener(this)

        editTextEmail.findViewById<AppCompatImageView>(R.id.search_by_title_img).isVisible(false)
        editTextPassword.findViewById<AppCompatImageView>(R.id.search_by_title_img).isVisible(false)
    }

    private fun registerUser() {
        val email = editTextEmail.text.toString().trim { it <= ' ' }
        val password: String = editTextPassword.text.toString().trim { it <= ' ' }
        if (email.isEmpty()) {
            Toast.makeText(context,"Email is required", Toast.LENGTH_LONG).show()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context,"Please enter a valid email", Toast.LENGTH_LONG).show()
            editTextEmail.requestFocus()
            return
        }
        if (password.isEmpty()) {
            Toast.makeText(context,"Password is required", Toast.LENGTH_LONG).show()
            editTextPassword.requestFocus()
            return
        }
        if (password.length < 6) {
            Toast.makeText(context, "Minimum length of password should be 6", Toast.LENGTH_SHORT).show()
            editTextPassword.requestFocus()
            return
        }
        progressBar.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    with (mSettings.edit()) {
                        putBoolean(IS_REGISTERED, true)
                        apply()
                    }
                    (context as BaseActivity).fragmentRouter.openNewsFragment()
                } else {
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        Toast.makeText(
                                context,
                                "You are already registered",
                                Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                                context,
                                task.exception!!.message,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonSignUp -> registerUser()
            R.id.textViewLogin -> {
                (context as BaseActivity).supportFragmentManager.popBackStack()
            }
        }
    }
}