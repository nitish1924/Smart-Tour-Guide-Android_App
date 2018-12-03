package com.example.nitis.smarttourapp

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.view.*
import com.google.android.gms.maps.SupportMapFragment
import android.content.Intent
import android.net.Uri
import android.preference.PreferenceManager


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailsFragemnt : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var param1: String? = null
    private var param2: String? = null
    private var lati = "0"
    private var longi = "0"
    private var temp = "No Data Available"
    private var tempDesc = "No Data Available"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        lati = list.latitude!!
        longi = list.longitude!!

    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        // Add a marker in Sydney and move the camera
        val locOnMap = LatLng(lati.toDouble(), longi.toDouble())
        mMap.addMarker(MarkerOptions().position(locOnMap).title(list.name))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locOnMap, 18.0F))
    }

    inner class getWeather(view: View) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            if (params[0] != null) {
                val result = MyUtility.downloadJSONusingHTTPGetRequest(params[0]!!)
                if (result == null) {
                    return "abc"
                }
                return result!!
            }
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.i("weather in detail page", result)
            try {
                val data = Gson().fromJson(result, WeatherData::class.java)
                tempDesc = data.weather!![0]!!.description.toString()
                temp = data.main!!.temp.toString()

            } catch (e: Exception) {
                e.printStackTrace()
            }
            Picasso.get().load(list.photoUrl).into(view!!.detailfragimage)

            view!!.detailfradaddress.text = list.address
            view!!.detailfragname.text = list.name
            view!!.detailfragcontact.text = list.contact
            view!!.detailfragdescription.text = list.description
            view!!.detailprice.text = list.price
            try{
                var numericRating: Float = (list.rating!!.toFloat() / 2.0f)
                view!!.detailfragrating.rating = numericRating
            }
            catch(e:Exception){
                view!!.detailfragrating.rating = 0f
            }


            view!!.detailfragtemp.text = temp
            view!!.detailfragtempdesc.text = tempDesc


        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        getWeather(view).execute("https://api.openweathermap.org/data/2.5/weather?lat=" + lati + "&lon=" + longi + "&appid=43ca8af9d4bfa71d55815e0c9ca37218&units=imperial")

        val mapFrag = this.childFragmentManager.findFragmentById(R.id.detailsMap) as SupportMapFragment?
        try {
            mapFrag!!.getMapAsync(this)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        view.navigateBtn.setOnClickListener {
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
            var sourceLatitude = sharedPref.getString("GPSLat", "0")
            var sourceLongitude = sharedPref.getString("GPSLong", "0")
            var destinationLatitude = lati
            var destinationLongitude = longi
            val uri = "http://maps.google.com/maps?saddr=$sourceLatitude,$sourceLongitude&daddr=$destinationLatitude,$destinationLongitude"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }

        return view
    }

    companion object {
        lateinit var list: WishList
        @JvmStatic
        fun newInstance(param1: WishList) =
                DetailsFragemnt().apply {
                    arguments = Bundle().apply {
                        list = param1
                    }
                }
    }
}
