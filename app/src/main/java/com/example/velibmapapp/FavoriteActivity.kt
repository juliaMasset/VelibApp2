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

    var listView = findViewById<ListView>(R.id.listView)

    val helper = DataBaseHandler(this)

    val arrayList: ArrayList<Station> = helper.getInfo()



    val listItems = arrayOfNulls<String>(arrayList.size)

    for (i in arrayList.indices) {
        val recipe = arrayList[i]
        listItems[i] = recipe.toString()
    }

    val adapter = FavoriteAdapter(this, arrayList)
    listView.adapter = adapter
}
}