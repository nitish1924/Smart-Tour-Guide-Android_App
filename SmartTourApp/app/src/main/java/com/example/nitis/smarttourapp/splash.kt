package com.example.nitis.smarttourapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_splash.*

class splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setSupportActionBar(splashtoolbar)
        val menuBar = supportActionBar
        menuBar!!.title = "Smart Tour Guide"
        menuBar.setDisplayShowHomeEnabled(true)
        menuBar.setLogo(R.mipmap.ic_launcher)
        menuBar.setDisplayUseLogoEnabled(true)

      val mysplash = object : Thread() {
          override fun run() {
              try {
                  Thread.sleep(3000)
                  val aftersplash = Intent(baseContext,MainActivity::class.java)
                  startActivity(aftersplash)
              }catch (e: Exception){
                  e.printStackTrace()
              }
          }

      }
        mysplash.start()
    }
}
