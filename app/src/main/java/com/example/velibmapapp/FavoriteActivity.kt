package com.example.velibmapapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoriteActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)


        val go_back = findViewById<ImageView>(R.id.go_back)
        go_back.setOnClickListener {
            finish();
        }

    }

   /* A FINIR : pour afficher tableau

   val listView = findViewById<ListView>(R.id.listview)
    listView.adapter = StationAdapter(this)

    private class StationAdapter(context: Context) : BaseAdapter() {

        private val mContext: Context

        init {
            mContext = context
        }

        override fun getCount(): Int {
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Station {
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {

        }

    }*/
}