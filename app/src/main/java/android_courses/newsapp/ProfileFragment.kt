package android_courses.newsapp

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException

class ProfileFragment : Fragment() {

    private val CHOOSE_IMAGE = 101
    var textView: TextView? = null
    var imageView: ImageView? = null
    var editText: EditText? = null
    var uriProfileImage: Uri? = null
    var progressBar: ProgressBar? = null
    var profileImageUrl: String? = null
    var mAuth: FirebaseAuth? = null
    var buttonSave: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        loadUserInformation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText = view.findViewById(R.id.editTextDisplayName)
        imageView = view.findViewById(R.id.imageView)
        progressBar = view.findViewById(R.id.progressbar)
        textView = view.findViewById(R.id.textViewVerified)
        imageView?.setOnClickListener { showImageChooser() }
        buttonSave =  view.findViewById(R.id.buttonSave)
        buttonSave?.setOnClickListener { saveUserInformation() }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth!!.currentUser == null) {
            //TODO:: Start SignInFragment
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadUserInformation() {
        val user = mAuth!!.currentUser
        if (user != null) {
            if (user.photoUrl != null) {
                imageView?.let {
                    Glide.with(this)
                        .load(user.photoUrl.toString())
                        .into(it)
                }
            }
            if (user.displayName != null) {
                editText!!.setText(user.displayName)
            }
            if (user.isEmailVerified) {
                textView!!.text = "Email Verified"
            } else {
                textView!!.text = "Email Not Verified (Click to Verify)"
                textView!!.setOnClickListener {
                    user.sendEmailVerification()
                        .addOnCompleteListener {
                            Toast.makeText(
                                context,
                                "Verification Email Sent",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
        }
    }

    private fun saveUserInformation() {
        val displayName = editText!!.text.toString()
        if (displayName.isEmpty()) {
            editText!!.error = "Name required"
            editText!!.requestFocus()
            return
        }
        val user = mAuth!!.currentUser
        if (user != null && profileImageUrl != null) {
            val profile = UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .setPhotoUri(Uri.parse(profileImageUrl))
                .build()
            user.updateProfile(profile)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Profile Updated",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
            uriProfileImage = data.data
            try {
                val source: ImageDecoder.Source? =
                    ImageDecoder.createSource(context!!.contentResolver, uriProfileImage!!)
                val bitmap = ImageDecoder.decodeBitmap(source!!);
                imageView?.setImageBitmap(bitmap)
                uploadImageToFirebaseStorage()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImageToFirebaseStorage() {
        /*
        val profileImageRef = FirebaseStorage.getInstance()
            .getReference("profilepics/" + System.currentTimeMillis() + ".jpg")
        if (uriProfileImage != null) {
            progressBar!!.visibility = View.VISIBLE
            profileImageRef.putFile(uriProfileImage!!)
                .addOnSuccessListener { taskSnapshot ->
                    progressBar!!.visibility = View.GONE
                    profileImageUrl = taskSnapshot.getDownloadUrl().toString()
                }
                .addOnFailureListener { e ->
                    progressBar!!.visibility = View.GONE
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
        }*/
    }

    private fun showImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE)
    }
}