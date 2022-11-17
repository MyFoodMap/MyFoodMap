package com.example.myfoodmap

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfoodmap.databinding.ActivityKeywordSearchBinding
import kotlinx.android.synthetic.main.activity_keyword_search.*
import kotlinx.android.synthetic.main.activity_naver_map.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KeywordSearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKeywordSearchBinding

    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK 719ec8dad17c5585c9e25ff8a79fcd96"  // REST API 키
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeywordSearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSearch.setOnClickListener() {
            var etTextKeyword=keyword.text.toString()
            searchKeyword(etTextKeyword)
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