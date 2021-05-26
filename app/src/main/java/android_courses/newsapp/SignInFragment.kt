package android_courses.newsapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment(), View.OnClickListener {

    var mAuth: FirebaseAuth? = null
    var editTextEmail: EditText? = null
    var editTextPassword:EditText? = null
    var progressBar: ProgressBar? = null
    var textViewSignUp: TextView? = null
    var buttonLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
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
        textViewSignUp?.setOnClickListener(this)
        buttonLogin = view.findViewById(R.id.buttonLogin)
        buttonLogin?.setOnClickListener(this)
    }

    private fun userLogin() {
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
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar!!.visibility = View.GONE
                if (task.isSuccessful) {
                   //TODO:: start ProfileFragment
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
        if (mAuth!!.currentUser != null) {
            //TODO::start ProfileFragment
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.textViewSignup -> {
                //TODO::start SignUpFragment
            }
            R.id.buttonLogin -> userLogin()
        }
    }

}