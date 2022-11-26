package com.example.myfoodmap

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class GalleryAdapter(): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    lateinit var imageList: ArrayList<Uri>
    lateinit var nameList: ArrayList<String>
    lateinit var context: Context
    // 생성자
    constructor(imageList: ArrayList<Uri>, nameList: ArrayList<String>, context: Context): this() {
        this.imageList=imageList
        this.nameList=nameList
        this.context=context
    }
    // 화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater=LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_profile_peed_layout, parent, false)
        return ViewHolder(view)
    }
    // 데이터 설정
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(imageList[position]) // 이미지 위치
            .into(holder.peedView) // 보여줄 위치
        holder.peedName.text = nameList[position]
    }
    // 아이템 개수
    override fun getItemCount(): Int {
        return imageList.size
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var peedView: ImageView = view.findViewById(R.id.itemProfilePeed_PeedPicture)
        var peedName: TextView = view.findViewById(R.id.itemProfilePeed_PeedName_TextView)
    }
}