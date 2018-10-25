package hhxk.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hhxk.pojo.Record
import hhxk.ygw.R
import kotlinx.android.synthetic.main.item_record.view.*
import java.util.ArrayList

class RecordAdapter(var recordList:ArrayList<Record>):RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_record,parent,false))

    override fun getItemCount()=recordList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.number.text=recordList[position].number
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}