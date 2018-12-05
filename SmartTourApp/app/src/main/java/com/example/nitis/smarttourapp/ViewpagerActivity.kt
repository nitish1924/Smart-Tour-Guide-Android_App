package com.example.nitis.smarttourapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.nitis.smarttourapp.DetailsFragemnt
import com.example.nitis.smarttourapp.R
import kotlinx.android.synthetic.main.activity_viewpager.*


class  ViewpagerActivity : AppCompatActivity() {

    //pagerAdapter
    var pagerAdapter:PagerAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)

        setSupportActionBar ( viewpagerToolbar )
        val menuBar = supportActionBar
        menuBar!!.title="View Pager"
        menuBar.setDisplayHomeAsUpEnabled(true)

        pagerAdapter = PagerAdapter(supportFragmentManager,applicationContext)
        pagerAdapter!!.addFragments(DetailsFragemnt())


        //pageradapter to viewpager
        viewPager.adapter=pagerAdapter
        //viewpagerwith tablayout

        tabLayout.setupWithViewPager(viewPager)
    }

}
