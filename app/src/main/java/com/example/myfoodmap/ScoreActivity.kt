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
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_score.*
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.activity_egg_score_test.*
import kotlinx.android.synthetic.main.activity_score.*
import kotlin.math.roundToInt

class ScoreActivity : AppCompatActivity() {
    private companion object {
        const val TAG = "게시들작성"
    }

    private lateinit var customProgress: CustomProgress
    private lateinit var postInfo: PostInfo
    private lateinit var user: FirebaseUser
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        //임시변수
        var restaurantName = "고씨네"
        var taste = 4.5
        var cost = 3.5
        var clean = 1.0
        user = FireBaseAuth.auth.currentUser!!

        //사용하는 변수
        postInfo = PostInfo(restaurantName)
        postInfo.saveEvaluation(taste,cost,clean)
        customProgress = CustomProgress(this)

        score_Photo.setOnClickListener {
            upload()
        }

        var egg_1=1
        var egg_2=1
        var egg_3=1
        var egg_4=1
        var egg_5=1
        var whatScore="Taste"

        score_TasteRange.setOnClickListener() {
            score_ScoreBackground.visibility= View.VISIBLE
            score_ScoreRange.visibility= View.VISIBLE
            egg_1=1
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            score_Score1.setImageResource(R.mipmap.score_zero)
            score_Score2.setImageResource(R.mipmap.score_zero)
            score_Score3.setImageResource(R.mipmap.score_zero)
            score_Score4.setImageResource(R.mipmap.score_zero)
            score_Score5.setImageResource(R.mipmap.score_zero)
            score_Score.text = "0.0"
            whatScore="Taste"
        }
        score_PriceRange.setOnClickListener() {
            score_ScoreBackground.visibility=View.VISIBLE
            score_ScoreRange.visibility= View.VISIBLE
            egg_1=1
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            score_Score1.setImageResource(R.mipmap.score_zero)
            score_Score2.setImageResource(R.mipmap.score_zero)
            score_Score3.setImageResource(R.mipmap.score_zero)
            score_Score4.setImageResource(R.mipmap.score_zero)
            score_Score5.setImageResource(R.mipmap.score_zero)
            score_Score.text = "0.0"
            whatScore="Price"
        }
        score_CleanRange.setOnClickListener() {
            score_ScoreBackground.visibility=View.VISIBLE
            score_ScoreRange.visibility= View.VISIBLE
            egg_1=1
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            score_Score1.setImageResource(R.mipmap.score_zero)
            score_Score2.setImageResource(R.mipmap.score_zero)
            score_Score3.setImageResource(R.mipmap.score_zero)
            score_Score4.setImageResource(R.mipmap.score_zero)
            score_Score5.setImageResource(R.mipmap.score_zero)
            score_Score.text = "0.0"
            whatScore="Clean"
        }
        score_Save.setOnClickListener() {
            when(whatScore) {
                "Taste" -> {
                    score_TasteScore.text=score_Score.text
                    score_Taste1.setImageDrawable(score_Score1.drawable)
                    score_Taste2.setImageDrawable(score_Score2.drawable)
                    score_Taste3.setImageDrawable(score_Score3.drawable)
                    score_Taste4.setImageDrawable(score_Score4.drawable)
                    score_Taste5.setImageDrawable(score_Score5.drawable)
                }
                "Price" -> {
                    score_PriceScore.text=score_Score.text
                    score_Price1.setImageDrawable(score_Score1.drawable)
                    score_Price2.setImageDrawable(score_Score2.drawable)
                    score_Price3.setImageDrawable(score_Score3.drawable)
                    score_Price4.setImageDrawable(score_Score4.drawable)
                    score_Price5.setImageDrawable(score_Score5.drawable)
                }
                "Clean" -> {
                    score_CleanScore.text=score_Score.text
                    score_Clean1.setImageDrawable(score_Score1.drawable)
                    score_Clean2.setImageDrawable(score_Score2.drawable)
                    score_Clean3.setImageDrawable(score_Score3.drawable)
                    score_Clean4.setImageDrawable(score_Score4.drawable)
                    score_Clean5.setImageDrawable(score_Score5.drawable)
                }
            }
            score_ScoreRange.visibility= View.INVISIBLE
            score_ScoreBackground.visibility= View.INVISIBLE
            averageSum()
        }
        score_Score1.setOnClickListener() {
            score_Score2.setImageResource(R.mipmap.score_zero)
            score_Score3.setImageResource(R.mipmap.score_zero)
            score_Score4.setImageResource(R.mipmap.score_zero)
            score_Score5.setImageResource(R.mipmap.score_zero)
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            when (egg_1) {
                1 -> {
                    score_Score1.setImageResource(R.mipmap.score_half)
                    egg_1 = 2
                    score_Score.text = "0.5"
                }
                2 -> {
                    score_Score1.setImageResource(R.mipmap.score_full)
                    egg_1 = 3
                    score_Score.text = "1.0"
                }
                3 -> {
                    score_Score1.setImageResource(R.mipmap.score_zero)
                    egg_1 = 1
                    score_Score.text = "0.0"
                }
            }
        }
        score_Score2.setOnClickListener() {
            score_Score1.setImageResource(R.mipmap.score_full)
            score_Score3.setImageResource(R.mipmap.score_zero)
            score_Score4.setImageResource(R.mipmap.score_zero)
            score_Score5.setImageResource(R.mipmap.score_zero)
            egg_1=1
            egg_3=1
            egg_4=1
            egg_5=1
            when (egg_2) {
                1 -> {
                    score_Score2.setImageResource(R.mipmap.score_half)
                    egg_2 = 2
                    score_Score.text = "1.5"
                }
                2 -> {
                    score_Score2.setImageResource(R.mipmap.score_full)
                    egg_2 = 3
                    score_Score.text = "2.0"
                }
                3 -> {
                    score_Score2.setImageResource(R.mipmap.score_zero)
                    egg_2 = 1
                    score_Score.text = "1.0"
                }
            }
        }
        score_Score3.setOnClickListener() {
            score_Score1.setImageResource(R.mipmap.score_full)
            score_Score2.setImageResource(R.mipmap.score_full)
            score_Score4.setImageResource(R.mipmap.score_zero)
            score_Score5.setImageResource(R.mipmap.score_zero)
            egg_2=1
            egg_1=1
            egg_4=1
            egg_5=1
            when (egg_3) {
                1 -> {
                    score_Score3.setImageResource(R.mipmap.score_half)
                    egg_3 = 2
                    score_Score.text = "2.5"
                }
                2 -> {
                    score_Score3.setImageResource(R.mipmap.score_full)
                    egg_3 = 3
                    score_Score.text = "3.0"
                }
                3 -> {
                    score_Score3.setImageResource(R.mipmap.score_zero)
                    egg_3 = 1
                    score_Score.text = "2.0"
                }
            }
        }
        score_Score4.setOnClickListener() {
            score_Score1.setImageResource(R.mipmap.score_full)
            score_Score2.setImageResource(R.mipmap.score_full)
            score_Score3.setImageResource(R.mipmap.score_full)
            score_Score5.setImageResource(R.mipmap.score_zero)
            egg_2=1
            egg_3=1
            egg_1=1
            egg_5=1
            when (egg_4) {
                1 -> {
                    score_Score4.setImageResource(R.mipmap.score_half)
                    egg_4 = 2
                    score_Score.text = "3.5"
                }
                2 -> {
                    score_Score4.setImageResource(R.mipmap.score_full)
                    egg_4 = 3
                    score_Score.text = "4.0"
                }
                3 -> {
                    score_Score4.setImageResource(R.mipmap.score_zero)
                    egg_4 = 1
                    score_Score.text = "3.0"
                }
            }
        }
        score_Score5.setOnClickListener() {
            score_Score1.setImageResource(R.mipmap.score_full)
            score_Score2.setImageResource(R.mipmap.score_full)
            score_Score3.setImageResource(R.mipmap.score_full)
            score_Score4.setImageResource(R.mipmap.score_full)
            egg_2=1
            egg_3=1
            egg_4=1
            egg_1=1
            when (egg_5) {
                1 -> {
                    score_Score5.setImageResource(R.mipmap.score_half)
                    egg_5 = 2
                    score_Score.text = "4.5"
                }
                2 -> {
                    score_Score5.setImageResource(R.mipmap.score_full)
                    egg_5 = 3
                    score_Score.text = "5.0"
                }
                3 -> {
                    score_Score5.setImageResource(R.mipmap.score_zero)
                    egg_5 = 1
                    score_Score.text = "4.0"
                }
            }
        }
    }
    fun averageSum() {
        val a=score_TasteScore.text.toString().toDouble()
        val b=score_PriceScore.text.toString().toDouble()
        val c=score_CleanScore.text.toString().toDouble()
        val totalScore=(a+b+c)/3
        val averageScore=(totalScore*10.0).roundToInt()/10.0
        score_AverageScore.text="${averageScore}점"
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
            score_Photo.setImageURI(uri)
            postInfo.imageUri = uri
        }
    }

    private fun savePost(restaurantName:String){
        if(!postInfo.isEmpty()){
            if (user != null) {
                showProgressBar()
                // 사진 업로드
                FireBaseStorage.uploadPostingImage(user.uid, restaurantName, postInfo.imageUri!!,
                    mSuccessHandler = {
                        startToast("사진 저장 성공했습니다")

                        //업로드 사진 download uri 불러오기
                        FireBaseStorage.downloadImageUri(
                            user.uid,restaurantName,
                            mSuccessHandler = { uri ->
                                postInfo.imageUri = uri
                                Log.i(TAG,"사진 다운로드 성공")
                                startToast("사진 저장에 성공했습니다")

                                //게시물 dataBase에 등록
                                FireBaseDataBase.uploadPostingData(
                                    user.uid,user.email!!,postInfo,
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