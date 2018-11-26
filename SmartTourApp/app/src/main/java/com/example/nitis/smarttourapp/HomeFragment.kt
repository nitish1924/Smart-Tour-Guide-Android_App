package com.example.nitis.smarttourapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
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
import android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.view.View.OnClickListener
import android.widget.Toast
import android.widget.Toolbar
import com.example.nitis.smarttourapp.R.id.homeactivitytoolbar
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

        try{
            longitude = sharedPref.getString("currentLong", locationGps!!.longitude.toString())
            latitude = sharedPref.getString("currentLat",locationGps!!.latitude.toString())

            Log.i("try long",longitude)
            Log.i("try lat",latitude)
        }
        catch(e:Exception){
            longitude=locationGps!!.longitude.toString()
            latitude=locationGps!!.latitude.toString()
            Log.i("catch",longitude)
            Log.i("catch",latitude)
        }


        myview.BNight_Life.setOnClickListener(this)
        myview.BTop_picks.setOnClickListener(this)
        myview.Bcafe.setOnClickListener(this)
        myview.Bfood.setOnClickListener(this)
        myview.Bmovies.setOnClickListener(this)
        myview.Bshopping.setOnClickListener(this)
        myview.mylocationbtn.setOnClickListener(this)



        return myview
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
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationGps = location
//                            myresult.append("\nGPS ")
//                            myresult.append("\nLatitude : " + locationGps!!.latitude)
//                            myresult.append("\nLongitude : " + locationGps!!.longitude)
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
            }
            if (hasNetwork) {
                Log.d("CodeAndroidLocation", "hasGps")
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationNetwork = location
//                            myresult.append("\nNetwork ")
//                            myresult.append("\nLatitude : " + locationNetwork!!.latitude)
//                            myresult.append("\nLongitude : " + locationNetwork!!.longitude)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu, menu)
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
            try {
                val data = Gson().fromJson(result, Venues::class.java)
                Log.i("data", data.toString())
                var list = data.response!!.venues!!.toList()

                for (place in list) {
                    Log.i("place", place.toString())
                }
                if (list.size > 0) {
                    val transaction = activity!!.supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.homeframe, RecyclerViewFragment.newInstance(ArrayList(list)), "hdd")
                    transaction.addToBackStack(null)
                    transaction.commit()
                } else {
                    Toast.makeText(activity, "Nothing Nearby...Change location & Search!!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(activity, "Nothing Nearby...Change location & Search!!", Toast.LENGTH_SHORT).show()
            }


        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.top_picks -> {


            }
            R.id.cafe -> {


            }
            R.id.food -> {

            }

            R.id.nightlife -> {

            }
            R.id.shopping -> {

            }
            R.id.movies -> {

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
                query = "nightlife"
                executeURL()
            }
            R.id.Bcafe -> {
                query = "cafe"
                executeURL()
            }
            R.id.Bmovies -> {
                query = "movies"
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
        Log.i("long",longitude)
        Log.i("lat",latitude)
        if (query != "" || query != null) {
            val task = getJson()
            task.execute("https://api.foursquare.com/v2/venues/search?client_id=" + clientId + "&client_secret=" + clientSecret + "&ll=" + longitude + "," + latitude + "&query=" + query + "&v=20181124")
        } else {
            Toast.makeText(activity, "Please enter your preference", Toast.LENGTH_SHORT).show()
        }
    }
}




