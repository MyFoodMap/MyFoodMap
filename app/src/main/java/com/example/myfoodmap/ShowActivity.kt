package com.example.myfoodmap

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.activity_show.*
import kotlin.math.roundToInt

class ShowActivity : AppCompatActivity() {
    private lateinit var postInfo: PostInfo
    private lateinit var score1: ImageView
    private lateinit var score2: ImageView
    private lateinit var score3: ImageView
    private lateinit var score4: ImageView
    private lateinit var score5: ImageView

    private companion object {
        const val TAG = "게시글보기엑티비티"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        postInfo = intent.getSerializableExtra("post") as PostInfo

        Log.d(TAG,"${postInfo.restaurantName}")
        loadingPost(postInfo)
    }

    private fun loadingPost(postInfo: PostInfo){
        show_StoreName.text = postInfo.restaurantName
        Glide.with(this)
            .load(postInfo.imageUri)
            .into(show_Photo)
        //postInfo.tasteEvaluation.toDouble()
        //postInfo.costEvaluation.toDouble()
        //postInfo.cleanlinessEvaluation.toDouble()
        //postInfo.oneLineComment 한줄평
        show_TasteScore.text=postInfo.tasteEvaluation
        show_PriceScore.text=postInfo.costEvaluation
        show_CleanScore.text=postInfo.cleanlinessEvaluation
        val a=postInfo.tasteEvaluation.toDouble()
        score1=show_Taste1
        score2=show_Taste2
        score3=show_Taste3
        score4=show_Taste4
        score5=show_Taste5
        eggShow(a)
        val b=postInfo.costEvaluation.toDouble()
        score1=show_Price1
        score2=show_Price2
        score3=show_Price3
        score4=show_Price4
        score5=show_Price5
        eggShow(b)
        val c=postInfo.cleanlinessEvaluation.toDouble()
        score1=show_Clean1
        score2=show_Clean2
        score3=show_Clean3
        score4=show_Clean4
        score5=show_Clean5
        eggShow(c)
        val totalScore=(a+b+c)/3
        val averageScore=(totalScore*10.0).roundToInt()/10.0
        show_AverageScore.text= averageScore.toString()
        show_Review.text = postInfo.oneLineComment
    }

    private fun startToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
    fun eggShow(score:Double) {
        if(score==0.5) {
            score1.setImageResource(R.mipmap.score_half)
            score2.setImageResource(R.mipmap.score_zero)
            score3.setImageResource(R.mipmap.score_zero)
            score4.setImageResource(R.mipmap.score_zero)
            score5.setImageResource(R.mipmap.score_zero)
        } else if(score==1.0) {
            score1.setImageResource(R.mipmap.score_full)
            score2.setImageResource(R.mipmap.score_zero)
            score3.setImageResource(R.mipmap.score_zero)
            score4.setImageResource(R.mipmap.score_zero)
            score5.setImageResource(R.mipmap.score_zero)
        } else if(score==1.5) {
            score1.setImageResource(R.mipmap.score_full)
            score2.setImageResource(R.mipmap.score_half)
            score3.setImageResource(R.mipmap.score_zero)
            score4.setImageResource(R.mipmap.score_zero)
            score5.setImageResource(R.mipmap.score_zero)
        } else if(score==2.0) {
            score1.setImageResource(R.mipmap.score_full)
            score2.setImageResource(R.mipmap.score_full)
            score3.setImageResource(R.mipmap.score_zero)
            score4.setImageResource(R.mipmap.score_zero)
            score5.setImageResource(R.mipmap.score_zero)
        } else if(score==2.5) {
            score1.setImageResource(R.mipmap.score_full)
            score2.setImageResource(R.mipmap.score_full)
            score3.setImageResource(R.mipmap.score_half)
            score4.setImageResource(R.mipmap.score_zero)
            score5.setImageResource(R.mipmap.score_zero)
        } else if(score==3.0) {
            score1.setImageResource(R.mipmap.score_full)
            score2.setImageResource(R.mipmap.score_full)
            score3.setImageResource(R.mipmap.score_full)
            score4.setImageResource(R.mipmap.score_zero)
            score5.setImageResource(R.mipmap.score_zero)
        } else if(score==3.5) {
            score1.setImageResource(R.mipmap.score_full)
            score2.setImageResource(R.mipmap.score_full)
            score3.setImageResource(R.mipmap.score_full)
            score4.setImageResource(R.mipmap.score_half)
            score5.setImageResource(R.mipmap.score_zero)
        } else if(score==4.0) {
            score1.setImageResource(R.mipmap.score_full)
            score2.setImageResource(R.mipmap.score_full)
            score3.setImageResource(R.mipmap.score_full)
            score4.setImageResource(R.mipmap.score_full)
            score5.setImageResource(R.mipmap.score_zero)
        } else if(score==4.5) {
            score1.setImageResource(R.mipmap.score_full)
            score2.setImageResource(R.mipmap.score_full)
            score3.setImageResource(R.mipmap.score_full)
            score4.setImageResource(R.mipmap.score_half)
            score5.setImageResource(R.mipmap.score_zero)
        } else if(score==5.0) {
            score1.setImageResource(R.mipmap.score_full)
            score2.setImageResource(R.mipmap.score_full)
            score3.setImageResource(R.mipmap.score_full)
            score4.setImageResource(R.mipmap.score_full)
            score5.setImageResource(R.mipmap.score_full)
        }
    }
}