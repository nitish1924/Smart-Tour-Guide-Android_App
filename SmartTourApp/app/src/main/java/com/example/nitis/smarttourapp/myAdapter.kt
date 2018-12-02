package com.example.nitis.smarttourapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.graphics.Color
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view.view.*
import java.lang.Exception
import java.text.DecimalFormat


class MyAdapter(private val list: ArrayList<Venue?>, context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    //var myListener : MyItemClickListener ? = null
    private var onClick: OnItemClicked? = null
    private var onLongClick: OnItemClicked? = null
//    private var context1=FragmentActivity()

    interface OnItemClicked {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

//
//    override fun onAttach(activity: Activity) {
//        context1=activity as FragmentActivity
//        super.onAttach(activity)
//
//    }

    var myContext: Context? = null

    init {

        this.myContext = context
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var img: ImageView
        var title: TextView
        var placeAddress: TextView
        var placeDistance: TextView
        var description: TextView
        var open: TextView
        var rating: TextView
        var photoUrl: TextView

        init {
            img = v.findViewById(R.id.venueImage)
            title = v.findViewById(R.id.title)
            placeAddress = v.findViewById(R.id.address)
            placeDistance = v.findViewById(R.id.distance)
            description = v.findViewById(R.id.description)
            open = v.findViewById(R.id.open)
            rating = v.findViewById(R.id.rating)
            photoUrl = v.findViewById(R.id.photoUrl)
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
        var addr = ""
        for (a in list[position]!!.location!!.formattedAddress!!) {
            addr += a
        }
        holder.placeAddress.text = addr
        var distanceMiles: Double = (list[position]!!.location!!.distance!!.toDouble() / 1609.0)
        var df = DecimalFormat("##.##")
        holder.placeDistance.text = df.format(distanceMiles).toString() + " Miles"
        holder.open.text = list[position]!!.isOpen
        holder.description.text = list[position]!!.description
        holder.rating.text = list[position]!!.rating
        holder.photoUrl.text = list[position]!!.photoUrl
        Picasso.get().load(list[position]!!.photoUrl).into(holder.img)

        holder.itemView.setOnClickListener {
            onClick!!.onItemClick(position)
        }

    }

    fun setOnClick(onClick: OnItemClicked) {
        this.onClick = onClick
    }

    fun setOnLongClick(onLongClick: OnItemClicked) {
        this.onLongClick = onLongClick
    }

    override fun getItemCount() = list.size


}