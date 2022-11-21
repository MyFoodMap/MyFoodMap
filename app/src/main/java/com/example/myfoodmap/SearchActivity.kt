package com.example.myfoodmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search_PlacePicture.setOnClickListener() {
            val intent= Intent(this,CafeteriaActivity::class.java)
            startActivity(intent)
        }
    }
}