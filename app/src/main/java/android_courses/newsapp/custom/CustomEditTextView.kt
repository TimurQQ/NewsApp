package android_courses.newsapp.custom

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android_courses.newsapp.R
import android_courses.newsapp.R.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout

class CustomEditTextView @JvmOverloads constructor(
        context: Context,
        attrs : AttributeSet? = null,
        defStyle : Int = 0
):ConstraintLayout(context, attrs, defStyle) {

    var hint : String = ""
    set(value) {
        field = value
        findViewById<EditText>(R.id.custom_edit_text_view).hint = value
    }

    var errorAction = {}

    var text : String = ""
        get() = findViewById<EditText>(R.id.custom_edit_text_view).text.toString()
        set(value) {
        field = value
        findViewById<EditText>(R.id.custom_edit_text_view).text = Editable.Factory.getInstance().newEditable(value)
    }

    var searchImg : Int = 0
        set(value) {
            field = value
            findViewById<AppCompatImageView>(R.id.search_by_title_img).setImageResource(value)
        }

    fun setActionOnClick(v: () -> Unit) {
         v.invoke()
    }
    fun showError(){
        errorAction.invoke()
    }

    init {
        View.inflate(context, layout.custom_edit_text_view, this)

        with (context.theme.obtainStyledAttributes(attrs, styleable.CustomEditTextView, 0, 0)) {
            hint = getString(styleable.CustomEditTextView_editHint) ?: ""
            text = getString(styleable.CustomEditTextView_getText) ?: ""
            searchImg = getResourceId(styleable.CustomEditTextView_img, R.drawable.ic_baseline_youtube_searched_for_24)
        }
    }
}