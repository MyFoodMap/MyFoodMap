package com.example.myfoodmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_score.*
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {
    private lateinit var postInfo: PostInfo

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
            .load(postInfo.imageUri.toString())
            .into(show_Photo)
        //postInfo.tasteEvaluation.toDouble()
        //postInfo.costEvaluation.toDouble()
        //postInfo.cleanlinessEvaluation.toDouble()
        //postInfo.oneLineComment 한줄평
    }

    private fun startToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

}