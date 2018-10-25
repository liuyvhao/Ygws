package hhxk.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hhxk.pojo.Bill
import hhxk.ygw.R
import kotlinx.android.synthetic.main.item_bill.view.*
import java.util.ArrayList

class BillAdapter(var billList: ArrayList<Bill>) : RecyclerView.Adapter<BillAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bill, parent, false))

    override fun getItemCount() = billList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.img.setImageURI(billList[position].img)
        holder.itemView.name.text = billList[position].name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}