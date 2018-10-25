package hhxk.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hhxk.pojo.ShareName
import hhxk.ygw.R
import kotlinx.android.synthetic.main.activity_personal.view.*
import java.util.ArrayList

class PositionAdapter(var positionList: ArrayList<ShareName>) : RecyclerView.Adapter<PositionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_position_share, parent, false))

    override fun getItemCount() = positionList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.uImg.setImageURI(positionList[position].img)
        holder.itemView.name.text = positionList[position].name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}