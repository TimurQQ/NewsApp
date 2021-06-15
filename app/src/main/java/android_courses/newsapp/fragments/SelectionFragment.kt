package android_courses.newsapp.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import android_courses.newsapp.R
import android_courses.newsapp.base.BaseActivity
import android_courses.newsapp.custom.CustomEditTextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment

class SelectionFragment : Fragment(R.layout.fragment_selection) {
    companion object {
        var KEY: String? = "Key"
        var KEY_WORD: String = "keyWord"
        var sharedPreferences: SharedPreferences? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KEY_WORD = "bit"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<CustomEditTextView>(R.id.search_by_title)
        view.findViewById<CustomEditTextView>(R.id.content_search)

        sharedPreferences = context?.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor? = sharedPreferences?.edit()

        val searchByTitleButton : AppCompatImageButton = view.findViewById(R.id.search_by_title_button)
        searchByTitleButton.setOnClickListener {

            editor?.putString(KEY_WORD, view.findViewById<CustomEditTextView>(R.id.search_by_title).text)
            editor?.apply()

            (requireActivity() as BaseActivity).fragmentRouter.openNewsFragment()
        }
    }
}