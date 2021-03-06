package com.example.nitis.smarttourapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.*
import android.view.*
import android.widget.Toast
import kotlin.collections.ArrayList

class RecyclerViewFragment : Fragment(), MyAdapter.OnItemClicked {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    companion object {
        private val ARG_PASS = "fragment"
        private var list = ArrayList<Venue?>()
        fun newInstance(list1: ArrayList<Venue?>): RecyclerViewFragment {
            val args = Bundle()
            list = list1
            args.putSerializable(ARG_PASS, list)
            val fragment = RecyclerViewFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onItemClick(position: Int) {
        var addr = ""
        for (a in list[position]!!.location!!.formattedAddress!!) {
            addr += a
        }
       var detail =WishList(
               user = "",
               name = list[position]!!.name,
               description = list[position]!!.description,
               rating = list[position]!!.rating,
               price = list[position]!!.price,
               contact = list[position]!!.contact,
               photoUrl =list[position]!!.photoUrl,
               latitude = list[position]!!.location!!.lat.toString(),
               longitude = list[position]!!.location!!.lng.toString(),
               address = addr
       )


        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.homeframe, DetailsFragemnt.newInstance(detail), "hdd")
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onItemLongClick(position: Int) {
        Toast.makeText(activity, "long click", Toast.LENGTH_SHORT).show()
    }


    //following function is just to retain the state on orientation change.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_recycler_view, container, false)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = MyAdapter(list, context!!)
        (viewAdapter as MyAdapter).setOnClick(this)
        (viewAdapter as MyAdapter).setOnLongClick(this)

        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }
        return view
    }


}
