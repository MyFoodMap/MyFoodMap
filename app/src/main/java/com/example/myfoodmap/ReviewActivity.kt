package com.example.myfoodmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_review.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_review.addressName1
import kotlinx.android.synthetic.main.activity_review.addressName2
import kotlinx.android.synthetic.main.activity_review.addressName3
import kotlinx.android.synthetic.main.activity_review.addressName4
import kotlinx.android.synthetic.main.activity_review.addressName5
import kotlinx.android.synthetic.main.activity_review.placeName1
import kotlinx.android.synthetic.main.activity_review.placeName2
import kotlinx.android.synthetic.main.activity_review.placeName3
import kotlinx.android.synthetic.main.activity_review.placeName4
import kotlinx.android.synthetic.main.activity_review.placeName5
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt
/* 기존의 ScoreAcivity 이름과 용도가 Review에 적합하다고 생각되어 수정 확인시 삭제 */
class ReviewActivity : AppCompatActivity() {
    private companion object {
        const val TAG = "게시들작성"
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK 719ec8dad17c5585c9e25ff8a79fcd96"  // REST API 키
    }

    private lateinit var customProgress: CustomProgress
    private lateinit var postInfo: PostInfo
    private lateinit var userInfo: UserInfo
    private lateinit var user: FirebaseUser
    private var xyList = ArrayList<Pair<String,String>>()

    private var x = "1.223232xxx"
    private var y = "1.223232xxx"
    private var x1 = "1.223232xxx"
    private var y1 = "1.223232xxx"
    private var x2 = "1.223232xxx"
    private var y2 = "1.223232xxx"
    private var x3 = "1.223232xxx"
    private var y3 = "1.223232xxx"
    private var x4 = "1.223232xxx"
    private var y4 = "1.223232xxx"
    private var x5 = "1.223232xxx"
    private var y5 = "1.223232xxx"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        //사용하는 변수
        customProgress = CustomProgress(this)
        user = FireBaseAuth.auth.currentUser!!
        postInfo = PostInfo()
        userInfo = intent.getSerializableExtra("user") as UserInfo

        review_Photo.setOnClickListener {
            upload()
        }


        var egg_1=1
        var egg_2=1
        var egg_3=1
        var egg_4=1
        var egg_5=1
        var whatScore="Taste"

        review_TasteRange.setOnClickListener {
            review_ScoreBackground.visibility= View.VISIBLE
            review_ScoreRange.visibility= View.VISIBLE
            egg_1=1
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            review_Score1.setImageResource(R.mipmap.score_zero)
            review_Score2.setImageResource(R.mipmap.score_zero)
            review_Score3.setImageResource(R.mipmap.score_zero)
            review_Score4.setImageResource(R.mipmap.score_zero)
            review_Score5.setImageResource(R.mipmap.score_zero)
            review_Score.text = "0.0"
            whatScore="Taste"
        }
        review_PriceRange.setOnClickListener {
            review_ScoreBackground.visibility=View.VISIBLE
            review_ScoreRange.visibility= View.VISIBLE
            egg_1=1
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            review_Score1.setImageResource(R.mipmap.score_zero)
            review_Score2.setImageResource(R.mipmap.score_zero)
            review_Score3.setImageResource(R.mipmap.score_zero)
            review_Score4.setImageResource(R.mipmap.score_zero)
            review_Score5.setImageResource(R.mipmap.score_zero)
            review_Score.text = "0.0"
            whatScore="Price"
        }
        review_CleanRange.setOnClickListener {
            review_ScoreBackground.visibility=View.VISIBLE
            review_ScoreRange.visibility= View.VISIBLE
            egg_1=1
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            review_Score1.setImageResource(R.mipmap.score_zero)
            review_Score2.setImageResource(R.mipmap.score_zero)
            review_Score3.setImageResource(R.mipmap.score_zero)
            review_Score4.setImageResource(R.mipmap.score_zero)
            review_Score5.setImageResource(R.mipmap.score_zero)
            review_Score.text = "0.0"
            whatScore="Clean"
        }
        review_Save.setOnClickListener {
            when(whatScore) {
                "Taste" -> {
                    review_TasteScore.text=review_Score.text
                    review_Taste1.setImageDrawable(review_Score1.drawable)
                    review_Taste2.setImageDrawable(review_Score2.drawable)
                    review_Taste3.setImageDrawable(review_Score3.drawable)
                    review_Taste4.setImageDrawable(review_Score4.drawable)
                    review_Taste5.setImageDrawable(review_Score5.drawable)
                }
                "Price" -> {
                    review_PriceScore.text=review_Score.text
                    review_Price1.setImageDrawable(review_Score1.drawable)
                    review_Price2.setImageDrawable(review_Score2.drawable)
                    review_Price3.setImageDrawable(review_Score3.drawable)
                    review_Price4.setImageDrawable(review_Score4.drawable)
                    review_Price5.setImageDrawable(review_Score5.drawable)
                }
                "Clean" -> {
                    review_CleanScore.text=review_Score.text
                    review_Clean1.setImageDrawable(review_Score1.drawable)
                    review_Clean2.setImageDrawable(review_Score2.drawable)
                    review_Clean3.setImageDrawable(review_Score3.drawable)
                    review_Clean4.setImageDrawable(review_Score4.drawable)
                    review_Clean5.setImageDrawable(review_Score5.drawable)
                }
            }
            review_ScoreRange.visibility= View.INVISIBLE
            review_ScoreBackground.visibility= View.INVISIBLE
            averageSum()
        }
        review_Score1.setOnClickListener {
            review_Score2.setImageResource(R.mipmap.score_zero)
            review_Score3.setImageResource(R.mipmap.score_zero)
            review_Score4.setImageResource(R.mipmap.score_zero)
            review_Score5.setImageResource(R.mipmap.score_zero)
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            when (egg_1) {
                1 -> {
                    review_Score1.setImageResource(R.mipmap.score_half)
                    egg_1 = 2
                    review_Score.text = "0.5"
                }
                2 -> {
                    review_Score1.setImageResource(R.mipmap.score_full)
                    egg_1 = 3
                    review_Score.text = "1.0"
                }
                3 -> {
                    review_Score1.setImageResource(R.mipmap.score_zero)
                    egg_1 = 1
                    review_Score.text = "0.0"
                }
            }
        }
        review_Score2.setOnClickListener {
            review_Score1.setImageResource(R.mipmap.score_full)
            review_Score3.setImageResource(R.mipmap.score_zero)
            review_Score4.setImageResource(R.mipmap.score_zero)
            review_Score5.setImageResource(R.mipmap.score_zero)
            egg_1=1
            egg_3=1
            egg_4=1
            egg_5=1
            when (egg_2) {
                1 -> {
                    review_Score2.setImageResource(R.mipmap.score_half)
                    egg_2 = 2
                    review_Score.text = "1.5"
                }
                2 -> {
                    review_Score2.setImageResource(R.mipmap.score_full)
                    egg_2 = 3
                    review_Score.text = "2.0"
                }
                3 -> {
                    review_Score2.setImageResource(R.mipmap.score_zero)
                    egg_2 = 1
                    review_Score.text = "1.0"
                }
            }
        }
        review_Score3.setOnClickListener {
            review_Score1.setImageResource(R.mipmap.score_full)
            review_Score2.setImageResource(R.mipmap.score_full)
            review_Score4.setImageResource(R.mipmap.score_zero)
            review_Score5.setImageResource(R.mipmap.score_zero)
            egg_2=1
            egg_1=1
            egg_4=1
            egg_5=1
            when (egg_3) {
                1 -> {
                    review_Score3.setImageResource(R.mipmap.score_half)
                    egg_3 = 2
                    review_Score.text = "2.5"
                }
                2 -> {
                    review_Score3.setImageResource(R.mipmap.score_full)
                    egg_3 = 3
                    review_Score.text = "3.0"
                }
                3 -> {
                    review_Score3.setImageResource(R.mipmap.score_zero)
                    egg_3 = 1
                    review_Score.text = "2.0"
                }
            }
        }
        review_Score4.setOnClickListener {
            review_Score1.setImageResource(R.mipmap.score_full)
            review_Score2.setImageResource(R.mipmap.score_full)
            review_Score3.setImageResource(R.mipmap.score_full)
            review_Score5.setImageResource(R.mipmap.score_zero)
            egg_2=1
            egg_3=1
            egg_1=1
            egg_5=1
            when (egg_4) {
                1 -> {
                    review_Score4.setImageResource(R.mipmap.score_half)
                    egg_4 = 2
                    review_Score.text = "3.5"
                }
                2 -> {
                    review_Score4.setImageResource(R.mipmap.score_full)
                    egg_4 = 3
                    review_Score.text = "4.0"
                }
                3 -> {
                    review_Score4.setImageResource(R.mipmap.score_zero)
                    egg_4 = 1
                    review_Score.text = "3.0"
                }
            }
        }
        review_Score5.setOnClickListener {
            review_Score1.setImageResource(R.mipmap.score_full)
            review_Score2.setImageResource(R.mipmap.score_full)
            review_Score3.setImageResource(R.mipmap.score_full)
            review_Score4.setImageResource(R.mipmap.score_full)
            egg_2=1
            egg_3=1
            egg_4=1
            egg_1=1
            when (egg_5) {
                1 -> {
                    review_Score5.setImageResource(R.mipmap.score_half)
                    egg_5 = 2
                    review_Score.text = "4.5"
                }
                2 -> {
                    review_Score5.setImageResource(R.mipmap.score_full)
                    egg_5 = 3
                    review_Score.text = "5.0"
                }
                3 -> {
                    review_Score5.setImageResource(R.mipmap.score_zero)
                    egg_5 = 1
                    review_Score.text = "4.0"
                }
            }
        }
        review_StoreSearch_Button.setOnClickListener {
            review_SearchRange.visibility= View.VISIBLE
            review_ScoreBackground.visibility= View.VISIBLE
            val etTextKeyword=review_StoreName_EditText.text.toString()
            searchKeyword(etTextKeyword)
        }
        review_AddressSearchResult1.setOnClickListener {
            review_SearchRange.visibility= View.INVISIBLE
            review_ScoreBackground.visibility= View.INVISIBLE
            review_StoreName_EditText.setText(placeName1.text)
            postInfo.placeSet(placeName1.text.toString(),addressName1.text.toString(),
                xyList[0].first,xyList[0].second)

        }
        review_AddressSearchResult2.setOnClickListener {
            review_SearchRange.visibility= View.INVISIBLE
            review_ScoreBackground.visibility= View.INVISIBLE
            review_StoreName_EditText.setText(placeName2.text)
            postInfo.placeSet(placeName2.text.toString(),addressName2.text.toString(),
                xyList[2].first,xyList[2].second)
        }
        review_AddressSearchResult3.setOnClickListener {
            review_SearchRange.visibility= View.INVISIBLE
            review_ScoreBackground.visibility= View.INVISIBLE
            postInfo.placeSet(placeName3.text.toString(),addressName3.text.toString(),
                xyList[3].first,xyList[3].second)

        }
        review_AddressSearchResult4.setOnClickListener {
            review_SearchRange.visibility= View.INVISIBLE
            review_ScoreBackground.visibility= View.INVISIBLE
            review_StoreName_EditText.setText(placeName4.text)
            postInfo.placeSet(placeName1.text.toString(),addressName1.text.toString(),
                xyList[4].first,xyList[4].second)
        }
        review_AddressSearchResult5.setOnClickListener {
            review_SearchRange.visibility= View.INVISIBLE
            review_ScoreBackground.visibility= View.INVISIBLE
            review_StoreName_EditText.setText(placeName5.text)

            postInfo.placeSet(placeName5.text.toString(), addressName5.text.toString(), x5, y5)
        }
        review_Register.setOnClickListener { savePost() }
    }
    fun averageSum() {
        val a=review_TasteScore.text.toString().toDouble()
        val b=review_PriceScore.text.toString().toDouble()
        val c=review_CleanScore.text.toString().toDouble()
        val totalScore=(a+b+c)/3
        val averageScore=(totalScore*10.0).roundToInt()/10.0
        review_AverageScore.text="${averageScore}점"
    }
    //사진
    private fun upload() {
        val intent= Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        activityResult.launch(intent)
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode== RESULT_OK && it.data!=null) {
            val uri=it.data!!.data // 사진 데이터 넣어야함
            review_Photo.setImageURI(uri)
            postInfo.imageUri = uri.toString()
            Glide.with(this)
                .load(uri)
                .into(review_Photo)
        }
    }

    //포스터 등록
    private fun savePost(){
        postInfo.set(review_Review.text.toString())
        postInfo.evaluationSet( review_TasteScore.text.toString(),review_PriceScore.text.toString(),review_CleanScore.text.toString(),
            review_AverageScore.text.toString())
        if(checkEmpty(postInfo)){
            showProgressBar()
            // 사진 업로드
            FireBaseStorage.uploadPostingImage(user.uid, postInfo.restaurantName, postInfo.imageUri.toUri(),
                mSuccessHandler = {
                    startToast("사진 저장 성공했습니다")

                    //업로드 사진 download uri 불러오기
                    FireBaseStorage.downloadImageUri(
                        user.uid,postInfo.restaurantName,
                        mSuccessHandler = { uri ->
                            postInfo.imageUri = uri.toString()
                            Log.i(TAG,"사진 다운로드 성공")
                            startToast("사진 저장에 성공했습니다")

                            //게시물 dataBase에 등록
                            FireBaseDataBase.uploadPostingData(
                                user.uid,userInfo.id,postInfo,
                                mSuccessHandlerUser = {startToast("개인 사용자 폴더에 저장 성공")
                                    hideProgressBar()},
                                mFailureHandlerUser = {e ->
                                    Log.w(TAG, "데이터베이스에 사용자 정보 추가 실패", e)
                                    hideProgressBar()},
                                mSuccessHandlerPost = {startToast("공용 식당 폴더에 저장 성공")},
                                mFailureHandlerPost = {e->
                                    Log.w(TAG, "데이터베이스에 식당 정보 추가 실패", e)
                                    hideProgressBar()})
                        },
                        mFailureHandler = {
                            startToast("사진 다운로드에 실패했습니다")
                            Log.e(TAG,"사진 다운로드 실패",it)
                            hideProgressBar()
                        })},
                mFailureHandler = {
                    startToast("사진 저장에 실패했습니다")
                    hideProgressBar()},
                mFailureHandlerException = {e->
                    startToast("애러발생")
                    Log.e(TAG,"사진 업로드 실패",e)
                    hideProgressBar()})

        }
    }

    private fun checkEmpty(postInfo: PostInfo):Boolean{
        if(postInfo.restaurantName.isBlank()){
            startToast("가게이름을 입력해주세요")
            return false
        }
        if(postInfo.imageUri.isBlank()){
            startToast("가게이름을 입력해주세요")
            return false
        }
        if(postInfo.tasteEvaluation.isBlank()){
            startToast("맛 평점을 선택해주세요")
            return false
        }
        if(postInfo.costEvaluation.isBlank()){
            startToast("가격 평점을 선택해주세요")
            return false
        }
        if(postInfo.cleanlinessEvaluation.isBlank()){
            startToast("평점 평점을 선택해주세요")
            return false
        }
        if(postInfo.oneLineComment.isBlank()){
            startToast("한줄평을 작성해주세요")
            return false
        }
        return true
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


                        val token=(it.documents[index].address_name).split(' ')
                        Log.d("Address", "$token")
                        when(index) {
                            0 -> {
                                placeName1.text="${it.documents[index].place_name}"
                                addressName1.text="${it.documents[index].address_name}"
                                xyList.add(Pair(it.documents[index].x,it.documents[index].y))
                            } 1 -> {
                                placeName2.text="${it.documents[index].place_name}"
                                addressName2.text="${it.documents[index].address_name}"
                                xyList.add(Pair(it.documents[index].x,it.documents[index].y))
                            } 2 -> {
                                placeName3.text="${it.documents[index].place_name}"
                                addressName3.text="${it.documents[index].address_name}"
                                xyList.add(Pair(it.documents[index].x,it.documents[index].y))
                            } 3 -> {
                                placeName4.text="${it.documents[index].place_name}"
                                addressName4.text="${it.documents[index].address_name}"
                                xyList.add(Pair(it.documents[index].x,it.documents[index].y))
                            } 4 -> {
                                placeName5.text="${it.documents[index].place_name}"
                                addressName5.text="${it.documents[index].address_name}"
                                xyList.add(Pair(it.documents[index].x,it.documents[index].y))

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