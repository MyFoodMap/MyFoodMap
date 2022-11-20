package com.example.myfoodmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

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
    }
}