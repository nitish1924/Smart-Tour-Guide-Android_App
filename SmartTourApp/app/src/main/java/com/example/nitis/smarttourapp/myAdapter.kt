package com.example.nitis.smarttourapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import java.text.DecimalFormat


class MyAdapter(private val list: ArrayList<Venue?>, context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    //var myListener : MyItemClickListener ? = null
    private var onClick: OnItemClicked? = null
    private var onLongClick: OnItemClicked? = null

    interface OnItemClicked {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    var myContext: Context? = null

    init {

        this.myContext = context
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var title: TextView
        var placeAddress: TextView
        var placeDistance: TextView
//        var image:ImageView
//        var description: TextView
//        var release: TextView
//        var checkBox:CheckBox
//        var overflowimg:ImageView


        init {
            title = v.findViewById(R.id.title)
            placeAddress = v.findViewById(R.id.address)
            placeDistance = v.findViewById(R.id.distance)
//            image = v.findViewById(R.id.movieImage)
//            description = v.findViewById(R.id.description)
//            release=v.findViewById(R.id.releaseDate)
//            checkBox=v.findViewById(R.id.checkbox)
//            overflowimg=v.findViewById(R.id.overflow)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return 0
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {
        var itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view, parent, false)
        // create a new view
        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.title.text = list[position]!!.name
        holder.placeAddress.text = list[position]!!.location!!.formattedAddress!![0]
        var distanceMiles:Double= (list[position]!!.location!!.distance!!.toDouble() /1609.0)
        var df = DecimalFormat("##.##")
        holder.placeDistance.text=df.format(distanceMiles).toString()+" Miles"

    }

    fun setOnClick(onClick: OnItemClicked) {
        this.onClick = onClick
    }

    fun setOnLongClick(onLongClick: OnItemClicked) {
        this.onLongClick = onLongClick
    }

    override fun getItemCount() = list.size


}