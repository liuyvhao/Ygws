package hhxk.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hhxk.pojo.News
import hhxk.util.OnItemClickListener
import hhxk.ygw.R
import kotlinx.android.synthetic.main.item_news.view.*
import java.util.ArrayList

class NewsAdapter(var newsList: ArrayList<News>, var mOnItemClickListener: OnItemClickListener) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.img.setImageURI(newsList[position].img)
        holder.itemView.name.text = newsList[position].name
        holder.itemView.source.text = newsList[position].source
        holder.itemView.time.text = newsList[position].time

        holder.itemView.setOnClickListener {
            var position = holder.layoutPosition
            mOnItemClickListener.onItemClick(holder.itemView, position)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}