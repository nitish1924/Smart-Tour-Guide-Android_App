package com.example.nitis.smarttourapp

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_signup.*

class signup : AppCompatActivity() {

    inner class postRequest : AsyncTask<String, Void, String>() {
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

        /**
         * After completing background task
         **/
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.i("signin result", result)
            if (result == "Registered Successfuly!") {
                Toast.makeText(this@signup, "Registered Successfuly! Continue Login", Toast.LENGTH_SHORT).show()
                val homeActivity = Intent(this@signup, MainActivity::class.java)
                startActivity(homeActivity)
            } else if (result == "All fields are Mandatory!") {
                Toast.makeText(this@signup, "All fields are Mandatory!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@signup, result, Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setSupportActionBar(signuptoolbar)
        val menuBar = supportActionBar
        menuBar!!.title = "Smart Tour Guide"
        menuBar.setDisplayShowHomeEnabled(true)
        menuBar.setLogo(R.mipmap.ic_launcher)
        menuBar.setDisplayUseLogoEnabled(true)

        signUp.setOnClickListener {
            val name = signupname.text.toString()
            val email = signupemail.text.toString()
            val contact = signupcontact.text.toString()
            val password = signuppwd.text.toString()
            val cnfrmPassword = signupconfirmpwd.text.toString()
            if (password != cnfrmPassword) {
                Toast.makeText(this, "Password Do not match", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(email, password, name, contact)
                val task = postRequest()
                task.execute("http://10.0.2.2:3000/register", Gson().toJson(user))
            }
        }
    }


}
