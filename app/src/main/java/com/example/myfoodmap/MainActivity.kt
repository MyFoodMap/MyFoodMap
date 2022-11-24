package com.example.myfoodmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 로딩화면, 계란 4번 누르면 ChooseActivity 이동
        mainactivity_loading_1.setOnClickListener {
            mainactivity_loading_1.visibility= View.INVISIBLE
        }
        mainactivity_loading_2.setOnClickListener {
            mainactivity_loading_2.visibility= View.INVISIBLE
        }
        mainactivity_loading_3.setOnClickListener {
            mainactivity_loading_3.visibility= View.INVISIBLE
        }
        mainactivity_loading_4.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}