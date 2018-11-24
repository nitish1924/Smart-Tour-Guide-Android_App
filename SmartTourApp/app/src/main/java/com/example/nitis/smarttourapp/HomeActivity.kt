package com.example.nitis.smarttourapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val Uname = sharedPref.getString("Uname", "Not Available")
        val Uemail = sharedPref.getString("Uemail", "Not Available")

        nameText.text=Uname
        emailText.text=Uemail
    }
}
