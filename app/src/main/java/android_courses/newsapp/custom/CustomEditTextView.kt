package android_courses.newsapp.custom

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android_courses.newsapp.R
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.custom_edit_text_view.view.*

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

    var text : String = ""
        get() = findViewById<EditText>(R.id.custom_edit_text_view).text.toString()
        set(value) {
        field = value
        findViewById<EditText>(R.id.custom_edit_text_view).text = Editable.Factory.getInstance().newEditable(value)
    }

    init {
        View.inflate(context, R.layout.custom_edit_text_view, this)

        with (context.theme.obtainStyledAttributes(attrs, R.styleable.CustomEditTextView, 0, 0)) {
            hint = getString(R.styleable.CustomEditTextView_editHint) ?: ""
            text = getString(R.styleable.CustomEditTextView_getText) ?: ""
        }
    }
}