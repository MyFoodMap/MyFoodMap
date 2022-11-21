package com.example.myfoodmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_app_main.*

class AppMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_main)
        appMain_Profile_Button.setOnClickListener() {
            val intent= Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }
        appMain_Search_Button.setOnClickListener() {
            val intent= Intent(this,SearchActivity::class.java)
            startActivity(intent)
        }
        appMain_Plus_Button.setOnClickListener() {
            val intent= Intent(this,ScoreActivity::class.java)
            startActivity(intent)
        }
    }
}