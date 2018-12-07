package com.example.nitis.smarttourapp

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.preference.PreferenceManager
import android.support.v4.app.FragmentActivity
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import kotlin.Exception


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
        // var description: TextView
        var open: TextView
        var rating: TextView
        var overflowImage: ImageView

        init {
            img = v.findViewById(R.id.venueImage)
            title = v.findViewById(R.id.title)
            placeAddress = v.findViewById(R.id.address)
            placeDistance = v.findViewById(R.id.distance)
            //  description = v.findViewById(R.id.description)
            open = v.findViewById(R.id.open)
            rating = v.findViewById(R.id.rating)
            overflowImage = v.findViewById(R.id.overflow)
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

    inner class addToWishList : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            if (params[0] != null) {
                Log.i("params1", params[1])
                val result = MyUtility.sendHttPostRequest(params[0]!!, params[1]!!)

                if (result == null) {
                    return "abc"
                }
                return result!!
            }
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Toast.makeText(myContext, "Added to Wishlist", Toast.LENGTH_SHORT).show()
        }
    }


    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.title.text = list[position]!!.name
        var addr = ""
        for (a in list[position]!!.location!!.formattedAddress!!) {
            addr += a
        }
        holder.placeAddress.text = "Address: " + addr
        var distanceMiles: Double = (list[position]!!.location!!.distance!!.toDouble() / 1609.0)
        var df = DecimalFormat("##.##")
        holder.placeDistance.text = df.format(distanceMiles).toString() + " Miles"
        try {
            if (list[position]!!.isOpen == "true") {
                holder.open.text = "Open Now"
            } else {
                holder.open.text = "Closed Now"
            }
        } catch (e: Exception) {
            holder.open.text = "Maybe Open Now"
        }
        // holder.description.text = list[position]!!.description
        try {
            if (list[position]!!.rating == null)
                holder.rating.text = ""
            else
                holder.rating.text = "Rating : " + list[position]!!.rating
        } catch (e: Exception) {
            holder.rating.text = ""
        }

        Picasso.get().load(list[position]!!.photoUrl).into(holder.img)

        holder.itemView.setOnClickListener {
            onClick!!.onItemClick(position)
        }
        holder.overflowImage.setOnClickListener {
            val popup = PopupMenu(myContext!!, it)
            val menuInflater = popup.menuInflater
            menuInflater.inflate(R.menu.popup_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {

                    R.id.addWishList -> {
                        val sharedPref = PreferenceManager.getDefaultSharedPreferences(myContext)
                        val Uemail = sharedPref.getString("Uemail", "Not Available")
                        var a = list[position]!!.price
                        var b =list[position]!!.photoUrl
                        var c=list[position]!!.rating
                        var d=list[position]!!.contact
                        if(a==null)
                            a=""
                        if(b==null)
                            b=""
                        if(c==null)
                            c=""
                        if(d==null)
                            d=""
                        var data = WishList(
                                user = Uemail,
                                name = list[position]!!.name,
                                description = list[position]!!.description,
                                rating = c,
                                price = a,
                                contact =d,
                                photoUrl = b,
                                latitude = list[position]!!.location!!.lat.toString(),
                                longitude = list[position]!!.location!!.lng.toString(),
                                address = addr
                        )
                        var dataJson = Gson().toJson(data)
                        try {
                            addToWishList().execute("http://10.0.2.2:3000/addwishlist", dataJson)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }


                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
            // show icon on the popup menu !!
            val menuHelper = MenuPopupHelper(this.myContext!!, popup.menu as MenuBuilder, it)
            menuHelper.setForceShowIcon(true)
            menuHelper.gravity = Gravity.END
            menuHelper.show()
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