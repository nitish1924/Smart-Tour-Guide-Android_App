package com.example.nitis.smarttourapp

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_signup.*

class signup : AppCompatActivity() {

    inner class postRequest: AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg params: String?): String {
            if(params[0]!=null){
                Log.i("params1",params[1])
                val result = MyUtility.sendHttPostRequest(params[0]!!,params[1]!!)

                if(result == null){
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
            Log.i("signin result",result)
            if(result !="abc"){
                val homeActivity = Intent(this@signup, MainActivity::class.java)
                startActivity(homeActivity)
            }
            else{
                Toast.makeText(this@signup,"Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        signUp.setOnClickListener{
            val name = signupname.text.toString()
            val email = signupemail.text.toString()
            val contact = signupcontact.text.toString()
            val password = signuppwd.text.toString()

            val user = User(email,password,name,contact)
            val task = postRequest()
            task.execute("http://10.0.2.2:3000/register", Gson().toJson(user))

        }
    }




}
