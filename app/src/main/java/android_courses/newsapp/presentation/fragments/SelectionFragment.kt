package android_courses.newsapp.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android_courses.newsapp.R
import android_courses.newsapp.base.BaseActivity
import android_courses.newsapp.custom.CustomEditTextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment

class SelectionFragment : Fragment(R.layout.fragment_selection) {

    companion object {
        var sharedPreferences: SharedPreferences? = null
        var KEY : String? = null
        var KEY_WORD : String? = null
    }
    lateinit var backButton: ImageButton
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = context?.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor? = sharedPreferences?.edit()

        view.findViewById<CustomEditTextView>(R.id.search_by_title).setActionOnClick {
            view.findViewById<AppCompatImageView>(R.id.search_by_title_img).setOnClickListener {
                editor?.putString(KEY_WORD, view.findViewById<CustomEditTextView>(R.id.search_by_title).text)
                editor?.apply()
                (requireActivity() as BaseActivity).fragmentRouter.openNewsFragment()
            }
        }

        backButton = view.findViewById(R.id.img_back_button)
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}