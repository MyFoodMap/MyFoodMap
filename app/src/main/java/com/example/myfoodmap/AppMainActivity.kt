package com.example.myfoodmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.NonNull
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.activity_app_main.*

class AppMainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_main)

        mapView = findViewById(R.id.login_Map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        appMain_Profile_Button.setOnClickListener() {
            val intent= Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }
        appMain_Search_Button.setOnClickListener() {
            val intent= Intent(this,SearchActivity::class.java)
            startActivity(intent)
        }
        appMain_Plus_Button.setOnClickListener() {
            val intent= Intent(this,ScoreActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onMapReady(@NonNull naverMap: NaverMap) {
        val uiSettings = naverMap.uiSettings

        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        uiSettings.isCompassEnabled = false
        uiSettings.isLocationButtonEnabled = true
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}