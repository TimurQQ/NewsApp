package android_courses.newsapp.Utill

import android.app.Activity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_article.view.*

fun ImageView.loadFromUrl(url: String?) {
    Glide.with(this)
        .load(url)
        .into(iv_article_image)
}

fun View.isVisible(visibility: Boolean) =
    if (visibility) this.visibility = View.VISIBLE else this.visibility = View.GONE



//fun Activity.observe(action:() -> Unit) = this.observe(viewLifecycleOwner, Observer {response ->})