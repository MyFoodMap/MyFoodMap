package com.example.myfoodmap

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {

    private companion object {
        const val TAG = "검색"
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK 719ec8dad17c5585c9e25ff8a79fcd96"  // REST API 키
    }

    var searchList = arrayListOf<PlaceSearchData>()
    lateinit var searchAdapter: PlaceSearchAdapter
    private lateinit var bookmarkList:HashMap<String,Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

/*
        bookmarkList = HashMap<String,Any>() //Key가 식당이름이야 이거 비교해서 일치하면 북마크 표사하면서 될듯
        FireBaseDataBase.loadBookMark(FireBaseAuth.user!!.email,
            mSuccessHandler = {result->
                if(result != null) bookmarkList = result.toObject()!!
                Log.d(TAG,"북마크정보 불러오기 성공")},
            mFailureHandler = {e-> Log.e(TAG,"북마크정보 불러오기 실패",e)})

*/

        search_DetailSearch_Button.setOnClickListener {
            searchAdapter = PlaceSearchAdapter(this, searchList)
            search_SearchResult_ListView.adapter = searchAdapter

            var etTextKeyword2=search_DetailSearch_EditText.text.toString()
            searchKeyword(etTextKeyword2)
        }
        search_SearchResult_ListView.setOnItemClickListener { adapterView, view, i, l ->
            when(searchList[i].bookmark) {
                "@mipmap/bookmark_no" -> {
                    searchList[i].bookmark="@mipmap/bookmark_plus"
                    searchAdapter.notifyDataSetChanged()
                    Toast.makeText(
                        applicationContext,
                        (i + 1).toString() + "번째 아이템이 북마크되었습니다..",
                        Toast.LENGTH_SHORT
                    ).show()
                    //데이터 베이스 등록
                    /*FireBaseDataBase.addBookMark(FireBaseAuth.user!!.email,searchList[i].placeName,"임시주소",
                                                mSuccessHandler = {startToast("북마크 등록")},
                                                mFailureHandler = {e->
                                                    startToast("북마크 등록 실패")
                                                    Log.e(TAG,"북마크 등록 실패",e)})

                     */
                }
                "@mipmap/bookmark_plus" -> {
                    searchList[i].bookmark="@mipmap/bookmark_no"
                    searchAdapter.notifyDataSetChanged()


                    Toast.makeText(
                        applicationContext,
                        (i + 1).toString() + "번째 아이템이 북마크가 해제되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    /*
                    //데이터 베이스 등록
                    FireBaseDataBase.delBookMark(FireBaseAuth.user!!.email,searchList[i].placeName,"임시주소",
                        mSuccessHandler = {startToast("북마크 삭제")},
                        mFailureHandler = {e->
                            startToast("북마크 삭제 실패")
                            Log.e(TAG,"북마크 삭제 실패",e)})
                            
                     */
                }
            }

        }
    }

    // 키워드 검색 함수
    private fun searchKeyword(keyword: String) {
        val retrofit = Retrofit.Builder()   // Retrofit 구성
            .baseUrl(SearchActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPI::class.java)   // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(SearchActivity.API_KEY, keyword)   // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<KakaoData> {
            override fun onResponse(
                call: Call<KakaoData>,
                response: Response<KakaoData>
            ) {
                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                Log.d("Test", "${response.body()}")
                searchList.removeAll(searchList)
                response.body()?.let {
                    for(index in 0 until it.documents.size) { // 키워드 검색으로 나온 데이터 출력
                        if(index>10) {break}
                        Log.d("Address", "${it.documents[index].place_name}") // 장소
                        Log.d("Address", "${it.documents[index].address_name}") // 주소
                        Log.d("Address", "${it.documents[index].x}") // 경도
                        Log.d("Address", "${it.documents[index].y}") // 위도
                        searchList.add(PlaceSearchData("@mipmap/basic_profile", "${it.documents[index].place_name}",
                            "${it.documents[index].address_name}", "@mipmap/bookmark_no",
                            "${it.documents[index].x}", "${it.documents[index].y}"))
                    }
                }
            }
            override fun onFailure(call: Call<KakaoData>, t: Throwable) {
                // 통신 실패
                Log.w("MainActivity", "통신 실패: ${t.message}")
            }
        })
        searchAdapter.notifyDataSetChanged()
    }

    private fun startToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

}