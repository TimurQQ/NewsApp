package android_courses.newsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android_courses.newsapp.R
import android_courses.newsapp.SignInViewModel
import androidx.fragment.app.Fragment

class SignInFragment : Fragment(){
    private val viewModel: SignInViewModel by lazy {
        SignInViewModel(requireActivity())
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
        viewModel.onViewCreated(view)
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }
}