package com.example.velibmapapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.velibmapapp.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val TAG = "MapsActivity"

class MapsActivity : AppCompatActivity(), OnMapReadyCallback{

    private var stations: MutableList<Station> = mutableListOf()
    private lateinit var binding: ActivityMapsBinding
    private lateinit var clusterManager: ClusterManager<Station>
    private var favoris = ArrayList<Long?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonClick = findViewById<ImageView>(R.id.button_click)
        buttonClick.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)

        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        if (ContextCompat.checkSelfPermission(this@MapsActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MapsActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this@MapsActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(this@MapsActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }

    }

    override fun onMapReady(mMap: GoogleMap) {

        setUpClusterer(mMap)

        clusterManager.setOnClusterItemInfoWindowClickListener { item ->

            val nameText = findViewById<TextView>(R.id.info_text)
            nameText.text = item.name

            val velibText = findViewById<TextView>(R.id.info_velib)
            velibText.text = item.bikes_available.toString()

            val evelibText = findViewById<TextView>(R.id.info_evelib)
            evelibText.text = item.ebikes_available.toString()

            val docksText = findViewById<TextView>(R.id.info_docks)
            docksText.text = item.num_docks_available.toString()

            val infos = findViewById<CardView>(R.id.card_view)
            infos.isVisible = true

            val close = findViewById<ImageView>(R.id.info_close)

            val favoriteButton = findViewById<Button>(R.id.favorite_button)

            close.setOnClickListener {
                infos.isVisible = false
            }
            val context = this

            favoriteButton.setOnClickListener {
               var station = Station(item.bikes_available, item.capacity, item.ebikes_available, item.last_reported, item.lat, item.lon, item.name, item.num_docks_available, item.stationCode, item.station_id)
                var db = DataBaseHandler(context)
                db.insertData(station)
            }



            false
        }

    }

    fun appendElement(arr: Array<Int?>, newElement: Int): Array<Int?> {
        val array = arr.copyOf(arr.size + 1)
        array[arr.size] = newElement
        return array
    }

    private fun setUpClusterer(it: GoogleMap) {

        // Position the map.
        it.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.86, 2.35), 10f))

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        clusterManager = ClusterManager(this, it)

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        it.setOnCameraIdleListener(clusterManager)
        it.setOnMarkerClickListener(clusterManager)

        synchroApi()

        clusterManager.addItems(stations)
        clusterManager.cluster()
    }

    private fun synchroApi() {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://94.247.183.221:8078/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        val service = retrofit.create(VelibApi::class.java)

        runBlocking {
            val resultStation = service.getStations()
            Log.d(TAG, "synchroApi: $resultStation")

            resultStation.map {
                stations.remove(it)
                stations.add(it)
            }

        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MapsActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

}



