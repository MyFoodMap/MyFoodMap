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
        image_fri_1.setOnClickListener() {
            image_fri_1.visibility= View.INVISIBLE
        }
        image_fri_2.setOnClickListener() {
            image_fri_2.visibility= View.INVISIBLE
        }
        image_fri_3.setOnClickListener() {
            image_fri_3.visibility= View.INVISIBLE
        }
        image_fri_4.setOnClickListener() {
            val intent= Intent(this,FunctionActivity::class.java)
            startActivity(intent)
        }
    }
}