package com.example.news.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.model.ArticlesItem

class NewsAdapter(var items:List<ArticlesItem>?): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class  ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val titl:TextView=itemView.findViewById(R.id.title)
        val auther:TextView=itemView.findViewById(R.id.auther)
        val datetime:TextView=itemView.findViewById(R.id.date_time)
        val image:ImageView=itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?:0 // lw list equal null return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item =items?.get(position)
        holder.titl.setText(item?.title)
        holder.auther.setText(item?.author)
        holder.datetime.setText(item?.publishedAt)
        Glide.with(holder.itemView)
            .load(item?.urlToImage).into(holder.image)
    }

    fun changeDate(data:List<ArticlesItem?>?){
        items = data as List<ArticlesItem>?
        notifyDataSetChanged()


    }
}