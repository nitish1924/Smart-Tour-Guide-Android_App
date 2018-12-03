package com.example.nitis.smarttourapp

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_sign_in.*
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.fragment_sign_in.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class sign_in : Fragment(), View.OnClickListener {

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
            if (result == "All fields are Mandatory!") {
                Toast.makeText(activity, result.toString(), Toast.LENGTH_SHORT).show()
            } else if (result != "abc") {
                try {
                    val user = Gson().fromJson(result, User::class.java)
                    val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
                    //now get Editor
                    val editor = sharedPref.edit()
                    //put your value
                    editor.putString("Uname", user.name)
                    editor.putString("Uemail", user.email)
                    editor.putString("Ucontact", user.contact)

                    //commits your edits
                    editor.commit()
                    val homeActivity = Intent(activity, HomeActivity::class.java)
                    startActivity(homeActivity)
                } catch (e: Exception) {
                    Toast.makeText(activity, result, Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(activity, "something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.getId()) {
            R.id.signin -> {
                // Toast.makeText(activity,"hello",Toast.LENGTH_SHORT).show()
                val email = signin_email.text.toString()
                val password = signin_pwd.text.toString()
                val user = User(email, password, "", "")
                val task = postRequest()
                task.execute("http://10.0.2.2:3000/signin", Gson().toJson(user))
            }
            R.id.signUp -> {
                val signupactivity = Intent(activity, signup::class.java)
                startActivity(signupactivity)
            }
            R.id.forgotPwd -> {
                Toast.makeText(activity,"forgot",Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private val ARG_PASS = "fragment2"
        fun newInstance(pass: Int): sign_in {
            val args = Bundle()
            args.putSerializable(ARG_PASS, pass)
            val fragment = sign_in()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val myview = inflater.inflate(R.layout.fragment_sign_in, container, false)
        val b = myview.findViewById<Button>(R.id.signUp)
        b.setOnClickListener(this)
        val a = myview.findViewById<Button>(R.id.signin)
        a.setOnClickListener(this)
        myview.forgotPwd.setOnClickListener(this)
        val sharedpreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = sharedpreferences.edit()
        editor.clear()
        editor.commit()

        return myview
    }
}
