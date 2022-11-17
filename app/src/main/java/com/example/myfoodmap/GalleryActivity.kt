package com.example.myfoodmap

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfoodmap.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {
    lateinit var binding: ActivityGalleryBinding
    lateinit var galleryAdapter: GalleryAdapter
    var imageList: ArrayList<Uri> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //adapter 초기화
        galleryAdapter = GalleryAdapter(imageList, this)

        //recyclerView 설정
        val gridLayoutManager = GridLayoutManager(this, 3) // 3개씩 보여주기
        binding.recyclerViewGallery.layoutManager = gridLayoutManager
        binding.recyclerViewGallery.adapter = galleryAdapter


        //버튼 이벤트
        binding.btnGallery.setOnClickListener {
            //갤러리 호출
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            //멀티 선택 기능
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            activityResult.launch(intent)
        }//Create
    }

    @SuppressLint("SuspiciousIndentation")
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        //결과 코드 OK, 결과값 null 아니면
        if(it.resultCode== RESULT_OK) {
            if(it.data!!.clipData != null) {// 멀티 이미지
                //선택한 이미지 개수
                val count=it.data!!.clipData!!.itemCount

                for(index in 0 until count) {
                    //이미지 담기
                    val imageUri=it.data!!.clipData!!.getItemAt(index).uri
                    //이미지 추가  // 다중선택일때 넣어야하는 데이터 imageUri
                    imageList.add(imageUri)
                }
            } else { //싱글 이미지 // 하나선택했을때 넣어야하는 데이터 imageSingleUri
                val imageSingleUri=it.data!!.data
                imageList.add(imageSingleUri!!)
            }
            galleryAdapter.notifyDataSetChanged()
        }
    }
}