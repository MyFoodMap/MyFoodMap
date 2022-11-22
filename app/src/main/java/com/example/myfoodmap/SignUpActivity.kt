package com.example.myfoodmap

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.example.myfoodmap.databinding.ActivityKeywordSearchBinding
import com.example.myfoodmap.databinding.ActivitySignUpBinding
import kotlinx.android.synthetic.main.activity_keyword_search.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding

    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK 719ec8dad17c5585c9e25ff8a79fcd96"  // REST API 키
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clickNo = getColor(R.color.dark_gray)
        val click = getColor(R.color.gray)

        signUp_Man_Button.setOnClickListener() {
            signUp_Woman_Button.setBackgroundColor(click)
            signUp_Man_Button.setBackgroundColor(clickNo)
        }
        signUp_Woman_Button.setOnClickListener() {
            signUp_Woman_Button.setBackgroundColor(clickNo)
            signUp_Man_Button.setBackgroundColor(click)
        }
        binding.signUpAddressSearchButton.setOnClickListener() {
            var etTextKeyword=signUp_SignUpAddress_EditText.text.toString()
            signUp_AddressSearch_EditText.setText(etTextKeyword)
            searchKeyword(etTextKeyword)
            signUp_SearchScroll.visibility= View.VISIBLE
            signUp_SearchScrollBackground.visibility= View.VISIBLE
        }
        binding.signUpAddressSearchButton2.setOnClickListener() {
            var etTextKeyword2=signUp_AddressSearch_EditText.text.toString()
            searchKeyword(etTextKeyword2)
        }
        signUp_AddressSearchResult1.setOnClickListener() {
            signUp_SearchScroll.visibility= View.INVISIBLE
            signUp_SearchScrollBackground.visibility= View.INVISIBLE
            signUp_SignUpAddress_EditText.setText(placeName1.text)
        }
        signUp_AddressSearchResult2.setOnClickListener() {
            signUp_SearchScroll.visibility= View.INVISIBLE
            signUp_SearchScrollBackground.visibility= View.INVISIBLE
            signUp_SignUpAddress_EditText.setText(placeName2.text)
        }
        signUp_AddressSearchResult3.setOnClickListener() {
            signUp_SearchScroll.visibility= View.INVISIBLE
            signUp_SearchScrollBackground.visibility= View.INVISIBLE
            signUp_SignUpAddress_EditText.setText(placeName3.text)
        }
        signUp_AddressSearchResult4.setOnClickListener() {
            signUp_SearchScroll.visibility= View.INVISIBLE
            signUp_SearchScrollBackground.visibility= View.INVISIBLE
            signUp_SignUpAddress_EditText.setText(placeName4.text)
        }
        signUp_AddressSearchResult5.setOnClickListener() {
            signUp_SearchScroll.visibility= View.INVISIBLE
            signUp_SearchScrollBackground.visibility= View.INVISIBLE
            signUp_SignUpAddress_EditText.setText(placeName5.text)
        }
    }

    // 키워드 검색 함수
    private fun searchKeyword(keyword: String) {
        val retrofit = Retrofit.Builder()   // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPI::class.java)   // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(API_KEY, keyword)   // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<KakaoData> {
            override fun onResponse(
                call: Call<KakaoData>,
                response: Response<KakaoData>
            ) {
                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                Log.d("Test", "${response.body()}")
                response.body()?.let {
                    for(index in 0 until it.documents.size) { // 키워드 검색으로 나온 데이터 출력
                        Log.d("Address", "${it.documents[index].place_name}") // 장소
                        Log.d("Address", "${it.documents[index].address_name}") // 주소
                        Log.d("Address", "${it.documents[index].x}") // 경도
                        Log.d("Address", "${it.documents[index].y}") // 위도
                        var token=(it.documents[index].address_name).split(' ')
                        Log.d("Address", "$token")
                        var siDo: List<String>
                        var guGunDong: List<String>
                        var dongEubMyeon: List<String>
                        when(index) {
                            0 -> {
                                placeName1.text="${it.documents[index].place_name}"
                                addressName1.text="${it.documents[index].address_name}"
                            } 1 -> {
                                placeName2.text="${it.documents[index].place_name}"
                                addressName2.text="${it.documents[index].address_name}"
                            } 2 -> {
                                placeName3.text="${it.documents[index].place_name}"
                                addressName3.text="${it.documents[index].address_name}"
                            } 3 -> {
                                placeName4.text="${it.documents[index].place_name}"
                                addressName4.text="${it.documents[index].address_name}"
                            } 4 -> {
                                placeName5.text="${it.documents[index].place_name}"
                                addressName5.text="${it.documents[index].address_name}"
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<KakaoData>, t: Throwable) {
                // 통신 실패
                Log.w("MainActivity", "통신 실패: ${t.message}")
            }
        })
    }
}