package com.example.myfoodmap

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myfoodmap.databinding.ActivityProfileBinding
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_search.*


class ProfileActivity : AppCompatActivity() {
    private companion object{
        const val TAG = "프로필엑티비티"
    }

    private lateinit var userInfo: UserInfo
    private var postInfoList: ArrayList<PostInfo> = ArrayList()
    private lateinit var bookmarkDataList:HashMap<String,HashMap<String,String>>

    lateinit var binding: ActivityProfileBinding

    var bookmarkList = arrayListOf<BookmarkData>()
    lateinit var bookmarkAdapter: BookmarkAdapter

    private lateinit var galleryAdapter: GalleryAdapter
    var imageList: ArrayList<Uri> = ArrayList()
    var nameList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //adapter 초기화
        galleryAdapter = GalleryAdapter(imageList, nameList, this)

        //recyclerView 설정
        val gridLayoutManager = GridLayoutManager(this, 3) // 3개씩 보여주기
        binding.profilePeedRecyclerView.layoutManager = gridLayoutManager
        binding.profilePeedRecyclerView.adapter = galleryAdapter

        //유저 정보 받아오기
        userInfo = intent.getSerializableExtra("user") as UserInfo
        userInfo.photoUri?.let {
            profile_BasicProfile.setImageURI(it)
        }
         bookmarkDataList = intent.getSerializableExtra("bookmark") as HashMap<String,HashMap<String,String>>

        profile_UserName.text = userInfo.nickname

        //데이터 베이스
        FireBaseDataBase.getPostingDataForUser(userInfo.id,
            mSuccessHandler = { result->
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
                for(post in postInfoList){
                    imageList.add(post.imageUri.toUri())
                    nameList.add(post.restaurantName)
                }
                if(postInfoList.isNotEmpty()) {
                    imageList.removeAt(0)
                    nameList.removeAt(0)
                }
                galleryAdapter.notifyDataSetChanged()
            },
            mFailureHandler = {e->
                startToast("프로필 게시물 정보 받아오기 실패")
                Log.e(TAG,"게시물 정보 받아오기 실패 :",e) }
        )

        //버튼 이벤트


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

            bookmarkDataList
            for(bookmarkRestaurantName in bookmarkDataList.keys)
                bookmarkList.add(BookmarkData("@mipmap/spoon_select_button", bookmarkRestaurantName, "mipmap/bookmark_plus"))
            bookmarkAdapter.notifyDataSetChanged()
        }

        galleryAdapter.setItemClickListener(object : GalleryAdapter.onItemClickListener{
            override fun OnClick(v: View, position: Int) {
                val intent = Intent(v.context,ShowActivity::class.java)
                intent.putExtra("post",postInfoList[position+1])
                startActivity(intent)
            }
        })

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


    }

    private fun startToast(msg:String){
         Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
   }
}