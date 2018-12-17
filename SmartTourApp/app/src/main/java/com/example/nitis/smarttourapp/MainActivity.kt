package com.example.nitis.smarttourapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setSupportActionBar(maintoolbar)
//        val menuBar = supportActionBar
//        menuBar!!.title = "Smart Tour Guide"
//        menuBar.setDisplayShowHomeEnabled(true)
//        menuBar.setLogo(R.mipmap.ic_launcher)
//        menuBar.setDisplayUseLogoEnabled(true)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame, sign_in.newInstance(1), "kuchbhi")
                .addToBackStack(null)
                .commit()
    }

    override fun onBackPressed() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame, sign_in.newInstance(1), "kuchbhi")
                .addToBackStack(null)
                .commit()
    }
}



