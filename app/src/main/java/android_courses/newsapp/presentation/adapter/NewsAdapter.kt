package android_courses.newsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android_courses.newsapp.R
import android_courses.newsapp.Utill.loadFromUrl
import android_courses.newsapp.domain.entity.Article
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.item_article.view.*

class NewsAdapter(val onClick: (Article) -> Unit, val onClickDownload: (String) -> Unit) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private var builder = GsonBuilder().setPrettyPrinting()
    private var gson = builder.create()
    var listOfItems : List<Article> = emptyList()
    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val download_button : AppCompatImageButton by lazy {
            itemView.findViewById<AppCompatImageButton>(R.id.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleViewHolder(
        LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_article, parent, false)
    )

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = listOfItems[position]
        holder.itemView.apply {
            iv_article_image.loadFromUrl(article.urlToImage)
            tv_title.text = article.title
            tv_description.text = article.description
            setOnClickListener {
               onClick.invoke(article)
            }
        }
        holder.download_button.setOnClickListener {
            val json: String = gson.toJson(article)
            val textRef = storageRef.child("/${mAuth.currentUser!!.uid}/${article.title}.txt")
            val data = json.toByteArray()
            val uploadTask: UploadTask = textRef.putBytes(data)
            uploadTask.addOnFailureListener {
                onClickDownload.invoke("failure :(")
            }.addOnSuccessListener {
                onClickDownload.invoke("success :)")
            }
        }
    }
}