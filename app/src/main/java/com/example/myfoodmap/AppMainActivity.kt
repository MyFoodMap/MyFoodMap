package com.example.myfoodmap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.toObject
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import android.view.View
import com.google.firebase.database.GenericTypeIndicator
import kotlinx.android.synthetic.main.activity_app_main.*


class AppMainActivity : AppCompatActivity(), OnMapReadyCallback, Overlay.OnClickListener {
    private companion object {
        const val TAG = "메인엑티비티"
    }

    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체

    private lateinit var userInfo: UserInfo
    private lateinit var bookmarkList:HashMap<String,HashMap<String,String>>
    private lateinit var postInfoList: ArrayList<PostInfo>
    private lateinit var customProgress: CustomProgress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_main)

        postInfoList = ArrayList<PostInfo>()
        customProgress = CustomProgress(this)

        //데이터 베이스 정보
        showProgressBar()


        mapView = findViewById(R.id.appMain_map)
        mapView.onCreate(savedInstanceState)



        userInfo = intent.getSerializableExtra("user") as UserInfo



        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        appMain_Profile_Button.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("user", userInfo)
            intent.putExtra("bookmark",bookmarkList)
            startActivity(intent)
        }
        appMain_Search_Button.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("bookmark",bookmarkList)
            startActivity(intent)
        }
        appMain_Plus_Button.setOnClickListener {
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("user", userInfo)
            startActivity(intent)
        }
    }

    override fun onMapReady(@NonNull naverMap: NaverMap) {
        FireBaseDataBase.loadBookMark(FireBaseAuth.user!!.email,
            mSuccessHandler = {result->
                if(result.data != null) {
                    bookmarkList = result.data as HashMap<String, HashMap<String, String>>
                    if(bookmarkList.isNotEmpty()){
                        Log.d(TAG, "북마크정보 불러오기 성공 ${bookmarkList.toString()}")

                        for (bookMark in bookmarkList.values) {
                            Log.d(TAG, "북마크정보 ${bookMark["x"]}, ${bookMark["y"]}")

                            //마커 추가
                            val markerTemp = Marker()
                            markerTemp.position =
                                LatLng(bookMark["y"]!!.toDouble(), bookMark["x"]!!.toDouble())
                            markerTemp.map = naverMap

                            markerTemp.width = 100
                            markerTemp.height = 100
                            markerTemp.icon = OverlayImage.fromResource(R.drawable.bookmark_marker)
                        }
                    }
                }else{
                    bookmarkList = HashMap()
                }
            },
            mFailureHandler = {e-> Log.e(TAG,"북마크정보 불러오기 실패",e)})

        FireBaseDataBase.getPostingDataForUser(userInfo.id,
            mSuccessHandler = { result ->
                for (document in result) {
                    Log.d(TAG,"문서 : ${document.data}")
                    val documentToObject = document.toObject<PostInfo>()
                    postInfoList.add(documentToObject)

                    //마커 추가
                    Log.d(TAG,"x: ${documentToObject.x}, y : ${documentToObject.y}")
                    val marker = Marker()
                    marker.position = LatLng(documentToObject.y.toDouble(),documentToObject.x.toDouble())
                    marker.map = naverMap

                    marker.width = 100
                    marker.height = 100
                    marker.icon = OverlayImage.fromResource(R.drawable.peed_marker)
                }
                Log.d(TAG, "포스터 정보 받아오기 성공")
                hideProgressBar()
            },
            mFailureHandler = { e ->
                Log.e(TAG, "포스터 정보 받아오기 실패", e)
                startToast("포스터 정보 받아오기 실패")
                hideProgressBar()
            })

//        // 지도상에 마커 표시
//        val marker = Marker()
//        marker.position = LatLng(37.6203077604657, 127.057193096323)
//        marker.map = naverMap
//
//        marker.width = 100
//        marker.height = 100
//        marker.icon = OverlayImage.fromResource(R.drawable.bookmark_marker)
//
//        val marker1 = Marker()
//        marker1.position = LatLng(37.6192404638865, 127.058270608867)
//        marker1.map = naverMap
//
//        marker1.width = 100
//        marker1.height = 100
//        marker1.icon = OverlayImage.fromResource(R.drawable.peed_marker)

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
        mapView.getMapAsync(this)

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

    override fun onClick(p0: Overlay): Boolean {
        TODO("Not yet implemented")
    }

}