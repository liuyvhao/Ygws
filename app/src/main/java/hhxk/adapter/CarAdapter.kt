package hhxk.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hhxk.pojo.Car
import hhxk.ygw.R
import kotlinx.android.synthetic.main.item_car.view.*
import java.util.ArrayList

class CarAdapter(var carList: ArrayList<Car>) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false))

    override fun getItemCount() = carList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.img.setImageURI(carList[position].img)
        holder.itemView.number.text = carList[position].number
        holder.itemView.address.text = carList[position].address
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}