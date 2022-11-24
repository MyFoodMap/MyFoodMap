package com.example.myfoodmap

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_store_picture.*

class StorePictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_picture)

        picture_single_upload.setOnClickListener {
            upload()
        }
        picture_multi_upload.setOnClickListener {
            val intent=Intent(this,GalleryActivity::class.java)
            startActivity(intent)
        }
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode== RESULT_OK && it.data!=null) {
            val uri=it.data!!.data // 사진 데이터 넣어야함
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