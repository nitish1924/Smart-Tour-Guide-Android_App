package com.example.nitis.smarttourapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class sign_in : Fragment(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0?.getId())

        {
            R.id.signUp->
            {
                val signupactivity = Intent(activity, signup::class.java)
                startActivity(signupactivity)

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
        return myview
    }
}
