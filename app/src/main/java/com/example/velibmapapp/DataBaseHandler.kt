package com.example.velibmapapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "dbStations"
val TABLE_NAME = "Stations"
val COL_BIKES = "bikes_available"
val COL_CAPACITY = "capacity"
val COL_eBiKES = "ebikes_available"
val COL_LREPORTED = "last_reported"
val COL_LAT = "lat"
val COL_LON = "lon"
val COL_NAME = "name"
val COL_DOCKS = "num_docks_available"
val COL_STATIONcode = "stationCode"
val COL_ID = "station_id"

class DataBaseHandler (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME +" (" + COL_BIKES + " INTEGER," + COL_CAPACITY +" INTEGER," + COL_eBiKES + " INTEGER," + COL_LREPORTED +" LONG," + COL_LAT + " DOUBLE," + COL_LON + " DOUBLE," + COL_NAME + " STRING," + COL_DOCKS +" INTEGER, " + COL_STATIONcode + " LONG," + COL_ID +" LONG)";

        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(station: Station) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_BIKES, station.bikes_available)
        cv.put(COL_CAPACITY, station.capacity)
        cv.put(COL_LREPORTED, station.last_reported)
        cv.put(COL_LAT, station.lat)
        cv.put(COL_LON, station.lon)
        cv.put(COL_NAME, station.name)
        cv.put(COL_DOCKS, station.num_docks_available)
        cv.put(COL_STATIONcode, station.stationCode)
        cv.put(COL_ID, station.station_id)

        var result = db.insert(TABLE_NAME,null,cv)
        if(result == -1.toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

    }

    fun getAll(): List<Station> {

        // A FINIR
        val db = this.readableDatabase
        val favoris = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        favoris.
        return
    }

}
