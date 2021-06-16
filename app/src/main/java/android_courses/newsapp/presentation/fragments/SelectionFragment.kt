package android_courses.newsapp.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android_courses.newsapp.NewsViewModel
import android_courses.newsapp.R
import android_courses.newsapp.base.BaseActivity
import android_courses.newsapp.custom.CustomEditTextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment

class SelectionFragment : Fragment(R.layout.fragment_selection) {

    companion object {
        var sharedPreferences: SharedPreferences? = null
        var KEY : String? = null
        var KEY_WORD : String? = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = context?.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor? = sharedPreferences?.edit()

        view.findViewById<AppCompatImageButton>(R.id.search_by_title_button).setOnClickListener {
            editor?.putString(KEY_WORD, view.findViewById<CustomEditTextView>(R.id.search_by_title).text)
            editor?.apply()
            (requireActivity() as BaseActivity).fragmentRouter.openNewsFragment()
        }
    }
}