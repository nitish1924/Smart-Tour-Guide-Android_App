package com.example.nitis.smarttourapp

import android.content.Intent
import android.location.Address
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_change_location.*
import android.widget.Toast
import android.location.LocationManager
import android.location.Geocoder
import android.preference.PreferenceManager


class ChangeLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var long1=""
    var lat1=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_location)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        //now get Editor
        val editor = sharedPref.edit()
        long1 = getIntent().getExtras().getString("longitude","0")
        lat1 =getIntent().getExtras().getString("latitude","0")

        editor.putString("currentLong", long1)
        editor.putString("currentLat", lat1)
        editor.commit()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        searchBtn.setOnClickListener{
            val location = search.text.toString()
            var addressList: List<Address>? = null
            if (location != null || location != "") {
                val geocoder = Geocoder(this)
                try {
                    addressList = geocoder.getFromLocationName(location, 1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                try {
                    var address = addressList!![0]
                    var temp = Location(LocationManager.GPS_PROVIDER,"","","",0,null,null,null,null,null,null)
                    temp.lat=address.latitude
                    temp.lng=address.longitude
                    if(address.latitude!=null&&address.longitude!=null){
//                        long1=address.longitude.toString()
//                        lat1=address.latitude.toString()

                        //put your value
                        editor.putString("currentLong", address.latitude.toString())
                        editor.putString("currentLat", address.longitude.toString())
                        editor.commit()
                    }
                    centerMapOnLocation(temp, location)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Can't find the address", Toast.LENGTH_SHORT).show()
                }

            }
        }

        doneBtn.setOnClickListener {
            val changeActivity = Intent(this,HomeActivity::class.java)
//            changeActivity.putExtra("longMap",long1!!)
//            changeActivity.putExtra("latMap",lat1!!)
            startActivity(changeActivity)
        }
    }

    fun centerMapOnLocation(location: Location?, title: String) {
        if (location != null) {
            val userLocation = LatLng(location.lat!!, location.lng!!)
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(userLocation).title(title))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18f))
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val locOnMap = LatLng(long1.toDouble(), lat1.toDouble())
        mMap.addMarker(MarkerOptions().position(locOnMap).title("Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locOnMap, 18.0F))
    }
}
