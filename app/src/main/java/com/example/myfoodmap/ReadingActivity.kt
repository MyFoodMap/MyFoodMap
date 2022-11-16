package com.example.myfoodmap

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_reading.*

class ReadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        btn_picture_upload.setOnClickListener() {
            upload()
        }
        picture_other.setOnClickListener() {
            val intent=Intent(this,MultiActivity::class.java)
            startActivity(intent)
        }
    }
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode== RESULT_OK && it.data!=null) {
            val uri=it.data!!.data
            val wiv=picture_1
            Glide.with(this)
                .load(uri)
                .into(wiv)
        }
    }
    private fun upload() {
        val intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        activityResult.launch(intent)
    }
}