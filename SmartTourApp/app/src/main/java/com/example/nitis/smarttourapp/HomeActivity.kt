package com.example.nitis.smarttourapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(homeactivitytoolbar)
        val menuBar = supportActionBar
        menuBar!!.title = "Smart Tour Guide"
        menuBar.setDisplayShowHomeEnabled(true)
        menuBar.setLogo(R.mipmap.ic_launcher)
        menuBar.setDisplayUseLogoEnabled(true)


        val toggle = ActionBarDrawerToggle(this, drawer_layout, homeactivitytoolbar, R.string.open_nav, R.string.close_nav)
        drawer_layout.addDrawerListener(toggle) // toggle is now drawer listener !
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val Uname = sharedPref.getString("Uname", "Not Available")
        val Uemail = sharedPref.getString("Uemail", "Not Available")
        val Ucontact = sharedPref.getString("Ucontact", "Not Available")

        //setting the nav drawer text fields
        val headerView = nav_view.getHeaderView(0)
        headerView.navheadertext1.text = Uname
        headerView.navheadertext2.text = Uemail
        headerView.navheadertext3.text = Ucontact

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.homeframe, HomeFragment.newInstance(1), "random")
                .addToBackStack(null)
                .commit()


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else
            super.onBackPressed()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.nav_Nightlife -> {

            }
            R.id.nav_cafe -> {

                true
            }
            R.id.nav_food -> {

            }
            R.id.nav_toppicks -> {

            }
            R.id.nav_shopping -> {

                true
            }
            R.id.nav_movies -> {

            }

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}