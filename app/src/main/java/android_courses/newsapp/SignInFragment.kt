package android_courses.newsapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android_courses.newsapp.Utill.Constants.Companion.APP_PREFERENCES
import android_courses.newsapp.Utill.Constants.Companion.IS_REGISTERED
import android_courses.newsapp.base.BaseActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment(), View.OnClickListener {

    lateinit var mAuth: FirebaseAuth
    lateinit var editTextEmail: EditText
    lateinit var editTextPassword:EditText
    lateinit var progressBar: ProgressBar
    lateinit var textViewSignUp: TextView
    lateinit var buttonLogin: Button
    lateinit var mSettings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        mSettings = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        progressBar = view.findViewById(R.id.progressbar)
        textViewSignUp = view.findViewById(R.id.textViewSignup)
        textViewSignUp.setOnClickListener(this)
        buttonLogin = view.findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener(this)
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
                        putBoolean(IS_REGISTERED, true)
                        apply()
                    }
                    (requireActivity() as BaseActivity).fragmentRouter.openNewsFragment()
                } else {
                    Toast.makeText(
                            context,
                            task.exception!!.message,
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        Log.d("AUTH", mAuth.currentUser.toString())
        if (mAuth.currentUser != null) {
            with (mSettings.edit()) {
                putBoolean(IS_REGISTERED, true)
                apply()
            }
            (requireActivity() as BaseActivity).fragmentRouter.openNewsFragment()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.textViewSignup -> {
                (requireActivity() as BaseActivity).fragmentRouter.openSignUpFragment()
            }
            R.id.buttonLogin -> userLogin()
        }
    }

}