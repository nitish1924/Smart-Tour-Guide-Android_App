package com.example.nitis.smarttourapp

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson

class PagerAdapter(fm: FragmentManager, context: Context) : FragmentStatePagerAdapter(fm) {
    private var list = ArrayList<WishList>()

    inner class getWishList : AsyncTask<String, Void, String>() {
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
            Log.i("getwishlist result", result)
            if (result != "abc") {
                try {
                    list = ArrayList(Gson().fromJson(result, Array<WishList>::class.java).asList())
                    notifyDataSetChanged()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
    }

    init {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val Uemail = sharedPref.getString("Uemail", "Not Available")
        val data = WishList(Uemail, "", "", "", "", "", "", "", "", "")
        getWishList().execute("http://10.0.2.2:3000/getwishlist", Gson().toJson(data))
    }

    // var mFm =fm
    var mFragmentItems: ArrayList<Fragment> = ArrayList()
    //var Titles:ArrayList<String> = ArrayList()


    fun addFragments(fragmentItem: Fragment) {
        mFragmentItems.add(fragmentItem)
    }

    override fun getItem(p0: Int): Fragment {
        //return mFragmentItems[p0]
        return DetailsFragemnt.newInstance(list[p0])
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position].name
    }

}