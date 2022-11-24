package com.example.myfoodmap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.toObject
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.widget.LocationButtonView
import kotlinx.android.synthetic.main.activity_app_main.*

class AppMainActivity : AppCompatActivity(), OnMapReadyCallback {
    private companion object {
        const val TAG = "메인"
    }

    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체

    private lateinit var userInfo: UserInfo
    private lateinit var postInfoList: ArrayList<PostInfo>
    private lateinit var customProgress: CustomProgress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_main)



        mapView = findViewById(R.id.appMain_map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        customProgress = CustomProgress(this)
        postInfoList = ArrayList<PostInfo>()

        showProgressBar()
        userInfo  = intent.getSerializableExtra("user") as UserInfo
        FireBaseDataBase.getPostingDataForUser(userInfo.id,
            mSuccessHandler = {result->
                for(document in result)
                    postInfoList.add(document.toObject<PostInfo>())
                Log.d(TAG,"포스터 정보 받아오기 성공")
                hideProgressBar()},
            mFailureHandler = {e->
                Log.e(TAG,"포스터 정보 받아오기 실패",e)
                startToast("포스터 정보 받아오기 실패")
                hideProgressBar()
            })

        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        appMain_Profile_Button.setOnClickListener {
            val intent= Intent(this,ProfileActivity::class.java)
            intent.putExtra("user",userInfo)
            startActivity(intent)
        }
        appMain_Search_Button.setOnClickListener {
            val intent= Intent(this,SearchActivity::class.java)
            startActivity(intent)
        }
        appMain_Plus_Button.setOnClickListener {
            val intent= Intent(this,ScoreActivity::class.java)
            intent.putExtra("user",userInfo)
            startActivity(intent)
        }
    }

    override fun onMapReady(@NonNull naverMap: NaverMap) {
        val uiSettings = naverMap.uiSettings

        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        uiSettings.isCompassEnabled = false
        uiSettings.isLocationButtonEnabled = false
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

    // 프로그레스바 보이기
    private fun showProgressBar() {
        blockLayoutTouch()
        customProgress.show()
    }

    // 프로그레스바 숨기기
    private fun hideProgressBar() {
        clearBlockLayoutTouch()
        customProgress.dismiss()
    }

    // 화면 터치 막기
    private fun blockLayoutTouch() {
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    // 화면 터치 풀기
    private fun clearBlockLayoutTouch() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun startToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

}