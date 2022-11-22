package com.example.myfoodmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_score.*

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
        score_Save_Button.setOnClickListener {
            savePost(restaurantName)
        }

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