package com.example.myfoodmap

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val clickNo = getColor(R.color.dark_gray)
        val click = getColor(R.color.gray)
        signUp_Man_Button.setOnClickListener() {
            signUp_Woman_Button.setBackgroundColor(click)
            signUp_Man_Button.setBackgroundColor(clickNo)
        }
        signUp_Woman_Button.setOnClickListener() {
            signUp_Woman_Button.setBackgroundColor(clickNo)
            signUp_Man_Button.setBackgroundColor(click)
        }
    }
}