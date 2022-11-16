package com.example.myfoodmap

import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.activity_naver_map.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NaverMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    private var et_text_address = "노원구 광운로 20" // editText 주소 초기화 ( 아무것도 입력하지 않은자 학교로 가거라 )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_naver_map)

        mapView = findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        btn_xy.setOnClickListener() {
            et_text_address=et_address.text.toString()
            getAddress() }
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

    fun getAddress() {
        val CLIENT_ID = "eee6pqxi1g"
        val CLIENT_SECRET = "CfiWfV8Ntt89VpcrsyjkLG0cgRLt6ZqZgQSks9sM"
        val BASE_URL_NAVER_API = "https://naveropenapi.apigw.ntruss.com/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_NAVER_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(NaverAPI::class.java)
        val callGetResponseXY = api.getResponseXY(CLIENT_ID, CLIENT_SECRET, et_text_address)

        callGetResponseXY.enqueue(object : Callback<NaverResponse> {
            override fun onResponse(
                call: Call<NaverResponse>,
                response: Response<NaverResponse>
            ) {
                Log.d("결과", "성공 : ${response.body()}")
                response.body()?.let {
                    val y = it.addresses[0].y
                    val x = it.addresses[0].x

                    tv_yx.text="x,y : $x, $y"

                    val cameraUpdate = CameraUpdate.scrollTo(LatLng(y.toDouble(), x.toDouble())) // 위도 경도 y x 주의
                        .animate(CameraAnimation.Fly)
                    naverMap.moveCamera(cameraUpdate)
                    val marker = Marker()
                    marker.position = LatLng(y.toDouble(), x.toDouble())
                    marker.map = naverMap
                }
            }

            override fun onFailure(call: Call<NaverResponse>, t: Throwable) {
                Log.d("결과:", "실패 : $t")
            }
        })
    }
}