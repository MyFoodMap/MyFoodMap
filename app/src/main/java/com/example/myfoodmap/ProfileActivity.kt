package com.example.myfoodmap

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfoodmap.databinding.ActivityGalleryBinding
import com.example.myfoodmap.databinding.ActivityProfileBinding
import com.google.firebase.firestore.ktx.toObject
import com.naver.maps.map.a.d
import kotlinx.android.synthetic.main.activity_profile.*
import com.bumptech.glide.Glide

class ProfileActivity : AppCompatActivity() {
    private companion object{
        const val TAG = "프로필"
    }
    private lateinit var userInfo: UserInfo
    private var postInfoList: ArrayList<PostInfo> = ArrayList()

    lateinit var binding: ActivityProfileBinding
    lateinit var galleryAdapter: GalleryAdapter
    var imageList: ArrayList<Uri> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //유저 정보 받아오기
        userInfo = intent.getSerializableExtra("user") as UserInfo
        userInfo.photoUri?.let {
            profile_BasicProfile.setImageURI(it)
        }

        //데이터 베이스 등록
        FireBaseDataBase.getPostingDataForUser(userInfo.id,
            mSuccessHandler = { result->
                val glide = Glide.with(this)
                for( document in result)
                    postInfoList.add(document.toObject<PostInfo>())
                startToast("정보받아오기 성공")
                Log.d(TAG,"정보받아오기 성공 : ${postInfoList[0].imageUri.toUri()}")
                //for(user in postInfoList)
                glide.load(postInfoList[0].imageUri.toUri()).into(profile_PeedPicture)
                profile_PeedName.text = postInfoList[0].restaurantName
            },
            mFailureHandler = {e->
                startToast("프로필 게시물 정보 받아오기 실패")
                Log.e(TAG,"게시물 정보 받아오기 실패 :",e) }
        )

        //adapter 초기화
        galleryAdapter = GalleryAdapter(imageList, this)

        //recyclerView 설정
        val gridLayoutManager = GridLayoutManager(this, 3) // 3개씩 보여주기
        binding.recyclerViewGallery.layoutManager = gridLayoutManager
        binding.recyclerViewGallery.adapter = galleryAdapter



        //버튼 이벤트
        binding.profilePeedPicture.setOnClickListener {
            //갤러리 호출
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            //멀티 선택 기능
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            activityResult.launch(intent)
        }//Create


        profile_PeedClickRange.setOnClickListener {
            profile_PeedPage.visibility= View.VISIBLE
            profile_BookmarkPage.visibility= View.INVISIBLE
            profile_SelectPeed.visibility=View.VISIBLE
            profile_SelectBookmark.visibility=View.INVISIBLE
        }

        profile_BookmarkClickRange.setOnClickListener {
            profile_PeedPage.visibility= View.INVISIBLE
            profile_BookmarkPage.visibility= View.VISIBLE
            profile_SelectPeed.visibility=View.INVISIBLE
            profile_SelectBookmark.visibility=View.VISIBLE
        }

        profile_BookmarkPlus.setOnClickListener {
            profile_BookmarkPlus.visibility=View.INVISIBLE
            profile_BookmarkNo.visibility=View.VISIBLE
        }
        profile_BookmarkNo.setOnClickListener {
            profile_BookmarkPlus.visibility=View.VISIBLE
            profile_BookmarkNo.visibility=View.INVISIBLE
        }
    }

    private fun startToast(msg:String){
         Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
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