package com.example.myfoodmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_function.*

class FunctionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function)

        btnPicture.setOnClickListener() {
            val intent= Intent(this,ReadingActivity::class.java)
            startActivity(intent)
        }
        btnNaverMap.setOnClickListener() {
            val intent= Intent(this,NaverMapActivity::class.java)
            startActivity(intent)
        }
    }
}