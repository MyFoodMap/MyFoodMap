package com.example.myfoodmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {
    private lateinit var postInfo: PostInfo

    private companion object {
        const val TAG = "게시글 보기"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        postInfo = PostInfo("고씨네") //여기에는 클린한 가게 이름

        FireBaseDataBase.getPostingDataForRestaurant(postInfo,
            mSuccessHandlerPost = { DocumentSnapshot->
                    loadingPost(DocumentSnapshot.toObject<PostInfo>()!!)
                    startToast("정보불러오기 성공") },
            mFailureHandlerPost = {exception ->
                //메인으로 가는 코드 추가하기
                Log.d(TAG, "정보 불러오기 실패 ", exception)})

    }

    private fun loadingPost(postInfo: PostInfo){
        show_StoreName.setText(postInfo.restaurantName)
        show_Photo.setImageURI(postInfo.imageUri.toUri())
        //postInfo.tasteEvaluation.toDouble() 맛 점수
        //postInfo.costEvaluation.toDouble() 가격 점수
        //postInfo.cleanlinessEvaluation.toDouble() 청결 점수
        //postInfo.oneLineComment 한줄평
    }

    private fun startToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

}