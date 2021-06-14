package android_courses.newsapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android_courses.newsapp.R
import android_courses.newsapp.custom.CustomEditTextView
import androidx.fragment.app.Fragment

class SelectionFragment : Fragment(R.layout.fragment_selection) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<CustomEditTextView>(R.id.search_by_title)
        view.findViewById<CustomEditTextView>(R.id.content_search)

    }
}