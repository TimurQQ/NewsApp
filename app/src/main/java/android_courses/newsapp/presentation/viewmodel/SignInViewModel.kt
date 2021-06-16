package android_courses.newsapp.presentation.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns
import android.view.View
import android.widget.*
import android_courses.newsapp.R
import android_courses.newsapp.Utill.Constants
import android_courses.newsapp.Utill.Constants.Companion.IS_REGISTERED
import android_courses.newsapp.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel(private val context: Context) : View.OnClickListener{
    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val mSettings: SharedPreferences by lazy {
        context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
    }
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewSignUp: TextView
    private lateinit var buttonLogin: Button

    fun onViewCreated(view: View) {
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        progressBar = view.findViewById(R.id.progressbar)
        textViewSignUp = view.findViewById(R.id.textViewSignup)
        buttonLogin = view.findViewById(R.id.buttonLogin)
        textViewSignUp.setOnClickListener(this)
        buttonLogin.setOnClickListener(this)
    }

    fun onStart() {
        if (mAuth.currentUser != null) {
            with (mSettings.edit()) {
                putBoolean(IS_REGISTERED, true)
                apply()
            }
            (context as BaseActivity).fragmentRouter.openNewsFragment()
        }
    }

    private fun userLogin() {
        val email = editTextEmail.text.toString().trim { it <= ' ' }
        val password: String = editTextPassword.text.toString().trim { it <= ' ' }
        if (email.isEmpty()) {
            editTextEmail.error = "Email is required"
            editTextEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.error = "Please enter a valid email"
            editTextEmail.requestFocus()
            return
        }
        if (password.isEmpty()) {
            editTextPassword.error = "Password is required"
            editTextPassword.requestFocus()
            return
        }
        if (password.length < 6) {
            editTextPassword.error = "Minimum length of password should be 6"
            editTextPassword.requestFocus()
            return
        }
        progressBar.visibility = View.VISIBLE
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        with (mSettings.edit()) {
                            putBoolean(android_courses.newsapp.Utill.Constants.IS_REGISTERED, true)
                            apply()
                        }
                        (context as BaseActivity).fragmentRouter.openNewsFragment()
                    } else {
                        Toast.makeText(
                                context,
                                task.exception!!.message,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.textViewSignup -> {
                (context as BaseActivity).fragmentRouter.openSignUpFragment()
            }
            R.id.buttonLogin -> userLogin()
        }
    }
}