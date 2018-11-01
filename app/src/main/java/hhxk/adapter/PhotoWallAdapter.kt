package hhxk.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hhxk.ygw.R
import kotlinx.android.synthetic.main.item_photo.view.*
import java.util.ArrayList

class PhotoWallAdapter(var imgList: ArrayList<String>) : RecyclerView.Adapter<PhotoWallAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false))

    override fun getItemCount() = imgList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.iv_thumbnail.setImageURI(imgList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}