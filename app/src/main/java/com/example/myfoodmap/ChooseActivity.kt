package com.example.myfoodmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_choose.*

class ChooseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        // 갤러리 구현화면으로 이동
        btnPicture.setOnClickListener {
            val intent= Intent(this,StorePictureActivity::class.java)
            startActivity(intent)
        }
        // 네이버지도 구현화면으로 이동
        btnNaverMap.setOnClickListener {
            val intent= Intent(this,NaverMapActivity::class.java)
            startActivity(intent)
        }
        btnKeywordSearch.setOnClickListener {
            val intent= Intent(this,KeywordSearchActivity::class.java)
            startActivity(intent)
        }

    }
}