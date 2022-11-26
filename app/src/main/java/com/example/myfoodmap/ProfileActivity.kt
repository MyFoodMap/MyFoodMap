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
import kotlinx.android.synthetic.main.activity_search.*
import com.bumptech.glide.Glide

class ProfileActivity : AppCompatActivity() {
    private companion object{
        const val TAG = "프로필엑티비티"
    }
    private lateinit var userInfo: UserInfo
    private var postInfoList: ArrayList<PostInfo> = ArrayList()

    lateinit var binding: ActivityProfileBinding

    var bookmarkList = arrayListOf<BookmarkData>()
    lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //유저 정보 받아오기
        userInfo = intent.getSerializableExtra("user") as UserInfo
        userInfo.photoUri?.let {
            profile_BasicProfile.setImageURI(it)
        }
        profile_UserName.text = userInfo.nickname

        //데이터 베이스

        FireBaseDataBase.getPostingDataForUser(userInfo.id,
            mSuccessHandler = { result->
                val glide = Glide.with(this)
                for( document in result) {
                    val post = PostInfo(document.data["restaurantName"].toString(),
                        document.data["tasteEvaluation"].toString(),document.data["costEvaluation"].toString(),document.data["cleanlinessEvaluation"].toString(),
                        document.data["totalEvalaution"].toString(),document.data["oneLineComment"].toString(),
                        document.data["address"].toString(),"0","0",
                        document.data["imageUri"].toString(),0)

                    postInfoList.add(post)
                    Log.d(TAG,"document : ${document.data}\n" +
                            "${post.restaurantName},${post.oneLineComment}\n" +
                            "${postInfoList.size}")
                }
                Log.d(TAG,"정보받아오기 성공 \n url : ${postInfoList[1].imageUri.toUri()}" +
                                "\nname :  ${postInfoList[1].restaurantName}")
                //for(user in postInfoList)
                glide.load(postInfoList[1].imageUri.toUri()).into(profile_PeedPicture)
                profile_PeedName.text = postInfoList[1].restaurantName
            },
            mFailureHandler = {e->
                startToast("프로필 게시물 정보 받아오기 실패")
                Log.e(TAG,"게시물 정보 받아오기 실패 :",e) }
        )

        //버튼 이벤트
        binding.profilePeedPicture.setOnClickListener {
        }//Create


        profile_PeedClickRange.setOnClickListener {
            profile_PeedPage.visibility= View.VISIBLE
            profile_BookmarkPage.visibility= View.INVISIBLE
            profile_SelectPeed.visibility=View.VISIBLE
            profile_SelectBookmark.visibility=View.INVISIBLE
            profile_Bookmark_ListView.visibility=View.INVISIBLE
        }

        profile_BookmarkClickRange.setOnClickListener {
            profile_PeedPage.visibility= View.INVISIBLE
            profile_BookmarkPage.visibility= View.VISIBLE
            profile_SelectPeed.visibility=View.INVISIBLE
            profile_SelectBookmark.visibility=View.VISIBLE
            profile_Bookmark_ListView.visibility=View.VISIBLE

            bookmarkAdapter = BookmarkAdapter(this, bookmarkList)
            profile_Bookmark_ListView.adapter = bookmarkAdapter

            bookmarkList.add(BookmarkData("@mipmap/spoon_select_button", "고씨네", "mipmap/bookmark_plus"))
            bookmarkList.add(BookmarkData("@mipmap/spoon_select_button", "이층집", "mipmap/bookmark_plus"))
            for(index in 0 until 10) {
                bookmarkList.add(BookmarkData("@mipmap/spoon_select_button", "고씨네", "mipmap/bookmark_plus"))
            }
            bookmarkAdapter.notifyDataSetChanged()
        }

        profile_BookmarkPlus.setOnClickListener {
            profile_BookmarkPlus.visibility=View.INVISIBLE
            profile_BookmarkNo.visibility=View.VISIBLE
        }
        profile_BookmarkNo.setOnClickListener {
            profile_BookmarkPlus.visibility=View.VISIBLE
            profile_BookmarkNo.visibility=View.INVISIBLE
        }

        profile_Bookmark_ListView.setOnItemClickListener { adapterView, view, i, l ->
            // 눌렀을때 뭐가 나와야하나 하고 우선 칸을 만들어봤어
        }

        profile_PeedPicture.setOnClickListener() {
            val intent = Intent(this, PeedViewActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startToast(msg:String){
         Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
   }
}