package hhxk.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hhxk.pojo.FMessage
import hhxk.ygw.R
import kotlinx.android.synthetic.main.item_friend_message.view.*
import java.util.ArrayList

class FriendMessageAdapter(var fMessageList: ArrayList<FMessage>) : RecyclerView.Adapter<FriendMessageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_friend_message, parent, false))

    override fun getItemCount() = fMessageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.img.setImageURI(fMessageList[position].img)
        holder.itemView.time.text=fMessageList[position].time
        holder.itemView.name.text=fMessageList[position].name
        holder.itemView.message.text=fMessageList[position].message
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}