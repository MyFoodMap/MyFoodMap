package com.example.myfoodmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private companion object{
        const val TAG = "프로필"
    }
    private lateinit var userInfo: UserInfo
    private var postInfoList: ArrayList<PostInfo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        FireBaseDataBase.getUserData(
            FireBaseAuth.user?.email!!,
            mSuccessHandler = {documentSnapshot ->
                userInfo = documentSnapshot.toObject<UserInfo>()!!

                profile_BasicProfile.setImageURI(userInfo.photoUri)
                //profile_UserName.setImageURI(userInfo.nickname) 이름 등록

                startToast("정보 불러오기 성공")
                Log.d(TAG,"정보 불러오기 성공") },
            mFailureHandler = {e->Log.e(TAG,"프로필 정보 불러오기 실패",e)})

        profile_PeedClickRange.setOnClickListener() {
            profile_PeedPage.visibility= View.VISIBLE
            profile_BookmarkPage.visibility= View.INVISIBLE
            profile_SelectPeed.visibility=View.VISIBLE
            profile_SelectBookmark.visibility=View.INVISIBLE
        }

        profile_BookmarkClickRange.setOnClickListener() {
            profile_PeedPage.visibility= View.INVISIBLE
            profile_BookmarkPage.visibility= View.VISIBLE
            profile_SelectPeed.visibility=View.INVISIBLE
            profile_SelectBookmark.visibility=View.VISIBLE
        }

        profile_PeedPin_Button.setOnClickListener() {
            profile_PeedPin_Button.visibility= View.INVISIBLE
            profile_PeedPinSelected_Button.visibility= View.VISIBLE
        }
        profile_PeedPinSelected_Button.setOnClickListener() {
            profile_PeedPin_Button.visibility= View.VISIBLE
            profile_PeedPinSelected_Button.visibility= View.INVISIBLE
        }
    }

    private fun startToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
}