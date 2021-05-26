package android_courses.newsapp

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class SignUpFragment : Fragment(), View.OnClickListener {

    var progressBar: ProgressBar? = null
    var editTextEmail: EditText? = null
    var editTextPassword:EditText? = null
    var buttonSignUp: Button? = null
    var textViewLogin: TextView? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        progressBar = view.findViewById(R.id.progressbar)
        buttonSignUp = view.findViewById(R.id.buttonSignUp)
        buttonSignUp?.setOnClickListener(this)
        textViewLogin = view.findViewById(R.id.textViewLogin)
        textViewLogin?.setOnClickListener(this)
    }

    private fun registerUser() {
        val email = editTextEmail!!.text.toString().trim { it <= ' ' }
        val password: String = editTextPassword?.text.toString().trim { it <= ' ' }
        if (email.isEmpty()) {
            editTextEmail!!.error = "Email is required"
            editTextEmail!!.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail!!.error = "Please enter a valid email"
            editTextEmail!!.requestFocus()
            return
        }
        if (password.isEmpty()) {
            editTextPassword?.error = "Password is required"
            editTextPassword?.requestFocus()
            return
        }
        if (password.length < 6) {
            editTextPassword?.error = "Minimum length of password should be 6"
            editTextPassword?.requestFocus()
            return
        }
        progressBar!!.visibility = View.VISIBLE
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar!!.visibility = View.GONE
                if (task.isSuccessful) {
                    //TODO:: Start ProfileFragment
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
                //TODO:: Start SignInFragment
            }
        }
    }
}