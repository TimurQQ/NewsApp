package android_courses.newsapp.Utill

import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_article.view.*

object Extensions {

    fun ImageView.loadFromUrl(url: String?) {
        Glide.with(this)
                .load(url)
                .into(iv_article_image)
    }
}