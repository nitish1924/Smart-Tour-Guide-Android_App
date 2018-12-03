package com.example.nitis.smarttourapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.content.pm.PackageManager
import android.location.LocationListener
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.view.Menu
import android.view.View.OnClickListener
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

private const val PERMISSION_REQUEST = 10

class HomeFragment : Fragment(), OnClickListener {
    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null
    private var locationNetwork: Location? = null
    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    private val clientId = "ON4ZCKAUD3ANSCVZ3TCS5Y1002ZZ4RAZDSYWWSTJTXQDQR4G"
    private val clientSecret = "IRJ4NU32WTIHZBGYMBQELV41SKRZN2SMG0V4KV0GNBRYSZ0I"
    private var query = ""
    private var longitude = ""
    private var latitude = ""
    private var masterList = ArrayList<Venue?>()
    private var detailsData = ArrayList<DetailsData>()

    companion object {
        private val ARG_PASS = "random"
        fun newInstance(pass: Int): HomeFragment {
            val args = Bundle()
            args.putSerializable(ARG_PASS, pass)
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        val Uname = sharedPref.getString("Uname", "Not Available")
        val Uemail = sharedPref.getString("Uemail", "Not Available")


        val myview = inflater.inflate(R.layout.fragment_home, container, false)
        getLocation()
        (activity as AppCompatActivity).supportActionBar?.title = "Welcome " + Uname
        disableView(myview)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) {
                enableView(myview)
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST)
            }
        } else {
            enableView(myview)
        }

        try {
            longitude = sharedPref.getString("currentLong", locationGps!!.longitude.toString())
            latitude = sharedPref.getString("currentLat", locationGps!!.latitude.toString())

            Log.i("try long", longitude)
            Log.i("try lat", latitude)
        } catch (e: Exception) {

            // ==================================== //null pointer exception sometimes===========================================================


            longitude = locationGps!!.longitude.toString()
            latitude = locationGps!!.latitude.toString()
            Log.i("catch", longitude)
            Log.i("catch", latitude)
        }
        getWeather().execute("https://api.openweathermap.org/data/2.5/weather?lat=" + longitude + "&lon=" + latitude + "&appid=43ca8af9d4bfa71d55815e0c9ca37218&units=imperial")

        val editor = sharedPref.edit()

        editor.putString("GPSLong", locationGps!!.latitude.toString())
        editor.putString("GPSLat", locationGps!!.longitude.toString())
        editor.commit()

        myview.BNight_Life.setOnClickListener(this)
        myview.BTop_picks.setOnClickListener(this)
        myview.Bcafe.setOnClickListener(this)
        myview.Bfood.setOnClickListener(this)
        myview.Bmovies.setOnClickListener(this)
        myview.Bshopping.setOnClickListener(this)
        myview.mylocationbtn.setOnClickListener(this)

        return myview
    }

    inner class getWeather : AsyncTask<String, Void, String>() {
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
            Log.i("json weather", result)
            try {
                val data = Gson().fromJson(result, WeatherData::class.java)
                homedesc.text = data.weather!![0]!!.description
                hometemp.text = data.main!!.temp.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }


    fun disableView(v: View) {
        try {
            v.mylocationbtn.isEnabled = false
            v.mylocationbtn.alpha = 0.5F
        } catch (e: Exception) {
            Toast.makeText(activity, "exception", Toast.LENGTH_SHORT).show()
        }

    }

    fun enableView(v: View) {
        try {
            v.mylocationbtn.isEnabled = true
            v.mylocationbtn.alpha = 1F


            //New fragment with map to select or search location
            // v.mylocationbtn.setOnClickListener { getLocation() }


            Toast.makeText(activity, "Done", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(activity, "exception", Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {

            if (hasGps) {
                Log.d("CodeAndroidLocation", "hasGps")
                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 0F, object : LocationListener {
                        override fun onLocationChanged(location: Location?) {
                            if (location != null) {
                                locationGps = location
                                Log.d("CodeAndroidLocation", " GPS Latitude : " + locationGps!!.latitude)
                                Log.d("CodeAndroidLocation", " GPS Longitude : " + locationGps!!.longitude)
                            }
                        }

                        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                        }

                        override fun onProviderEnabled(provider: String?) {

                        }

                        override fun onProviderDisabled(provider: String?) {

                        }

                    })

                    val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (localGpsLocation != null)
                        locationGps = localGpsLocation
                } catch (e: Exception) {

                }


            }
            if (hasNetwork) {
                Log.d("CodeAndroidLocation", "hasGps")
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationNetwork = location
                            Log.d("CodeAndroidLocation", " Network Latitude : " + locationNetwork!!.latitude)
                            Log.d("CodeAndroidLocation", " Network Longitude : " + locationNetwork!!.longitude)
                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }

                    override fun onProviderEnabled(provider: String?) {

                    }

                    override fun onProviderDisabled(provider: String?) {

                    }

                })

                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (localNetworkLocation != null)
                    locationNetwork = localNetworkLocation
            }

            if (locationGps != null && locationNetwork != null) {
                if (locationGps!!.accuracy > locationNetwork!!.accuracy) {
//                    myresult.append("\nNetwork ")
//                    myresult.append("\nLatitude : " + locationNetwork!!.latitude)
//                    myresult.append("\nLongitude : " + locationNetwork!!.longitude)
                    Log.d("CodeAndroidLocation", " Network Latitude : " + locationNetwork!!.latitude)
                    Log.d("CodeAndroidLocation", " Network Longitude : " + locationNetwork!!.longitude)
                } else {
//                    myresult.append("\nGPS ")
//                    myresult.append("\nLatitude : " + locationGps!!.latitude)
//                    myresult.append("\nLongitude : " + locationGps!!.longitude)
                    Log.d("CodeAndroidLocation", " GPS Latitude : " + locationGps!!.latitude)
                    Log.d("CodeAndroidLocation", " GPS Longitude : " + locationGps!!.longitude)
                }
            }

        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (context!!.checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if (requestAgain) {
                        Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "Go to settings and enable the permission", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (allSuccess)
                enableView(view!!)

        }
    }

    inner class getDetailList : AsyncTask<String, Void, String>() {
        var pos = 0
        override fun doInBackground(vararg params: String?): String {
            pos = params[1]!!.toInt()
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
            Log.i("details json received", result)

            if (result != "abc") {
                val data = Gson().fromJson(result, DetailsData::class.java)
                Log.i("Nitishdata", data.toString())

                detailsData.add(data)
            }



            for (place in masterList) {
                Log.i("place", place.toString())
            }
            if (pos == masterList.size - 1) {
                var count = 0
                for (venue in masterList) {
                    for (details in detailsData) {
                        Log.i("Ndesc", details.toString())
                        if (venue!!.id == details.response!!.venue!!.id) {
                            try {
                                // masterList[count]!!.description = details.response!!.venue!!.description
                                venue.description = details.response!!.venue!!.tips!!.groups!![0]!!.items!![0]!!.text
                                venue.isOpen = details.response!!.venue!!.popular!!.isOpen.toString()
                                venue.rating = details.response!!.venue!!.rating.toString()
                                venue.contact = details.response!!.venue!!.contact!!.phone
                                venue.price = details.response!!.venue!!.price!!.message
                                venue.photoUrl = details.response!!.venue!!.bestPhoto!!.prefix + "600x500" + details.response!!.venue!!.bestPhoto!!.suffix
                                Log.i("desc", venue.rating)
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                            }

                        } else {
                            venue.description = "Description Not Available"
                        }

                    }
                    count++

                }
                for (venue in masterList) {
                    //Log.i("nitish", venue!!.toString())
                }
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.homeframe, RecyclerViewFragment.newInstance(masterList), "hdd")
                transaction.addToBackStack(null)
                transaction.commit()
            }

        }
    }


    inner class getJson : AsyncTask<String, Void, String>() {
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
            Log.i("json received", result)
            var tempList = ArrayList<Venue?>()
            try {
                if (masterList.size > 0) {
                    tempList.removeAll(tempList)
                    masterList.removeAll(masterList)
                    detailsData.removeAll(detailsData)
                }
                val data = Gson().fromJson(result, Venues::class.java)
                Log.i("data", data.toString())
                tempList = ArrayList(data.response!!.venues!!.toList())
                var m = 0
                for (list in tempList) {
                    if (m < 5) {
                        masterList.add(list)
                    }
                    m++
                }
                for (place in masterList) {
                    Log.i("place", place.toString())
                }
                var pos = 0
                if (masterList.size > 0) {
                    for (venue in masterList) {
                        val venueId = venue!!.id
                        val url = "https://api.foursquare.com/v2/venues/" + venueId + "?client_id=ON4ZCKAUD3ANSCVZ3TCS5Y1002ZZ4RAZDSYWWSTJTXQDQR4G&client_secret=IRJ4NU32WTIHZBGYMBQELV41SKRZN2SMG0V4KV0GNBRYSZ0I&v=20181124"
                        getDetailList().execute(url, pos.toString())
                        pos++
                    }

                } else {
                    Toast.makeText(activity, "Nothing Nearby...Change location & Search!!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(activity, "Nothing Nearby...Change location & Search!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.home_logout -> {
                val sharedpreferences = PreferenceManager.getDefaultSharedPreferences(activity)
                val editor = sharedpreferences.edit()
                editor.clear()
                editor.commit()
                val changeActivity = Intent(activity, MainActivity::class.java)
                startActivity(changeActivity)
            }
            R.id.home_wishlist -> {
                val changeActivity = Intent(activity, ViewpagerActivity::class.java)
                startActivity(changeActivity)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.BTop_picks -> {
                query = "trending"
                executeURL()
            }
            R.id.Bfood -> {
                query = "food"
                executeURL()
            }
            R.id.BNight_Life -> {
                query = "bar"
                executeURL()
            }
            R.id.Bcafe -> {
                query = "cafe"
                executeURL()
            }
            R.id.Bmovies -> {
                query = "cinema"
                executeURL()
            }
            R.id.Bshopping -> {
                query = "shopping"
                executeURL()
            }
            R.id.mylocationbtn -> {
                val changeActivity = Intent(activity, ChangeLocationActivity::class.java)
                changeActivity.putExtra("longitude", locationGps!!.longitude.toString())
                changeActivity.putExtra("latitude", locationGps!!.latitude.toString())
                startActivity(changeActivity)
            }
        }
    }

    fun executeURL() {
        Log.i("long", longitude)
        Log.i("lat", latitude)
        if (query != "" || query != null) {
            val task = getJson()
            task.execute("https://api.foursquare.com/v2/venues/search?client_id=" + clientId + "&client_secret=" + clientSecret + "&ll=" + longitude + "," + latitude + "&query=" + query + "&v=20181124&limit10")
        } else {
            Toast.makeText(activity, "Please enter your preference", Toast.LENGTH_SHORT).show()
        }
    }
}




