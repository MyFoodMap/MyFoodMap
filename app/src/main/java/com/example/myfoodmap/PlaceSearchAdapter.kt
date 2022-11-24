package com.example.myfoodmap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class PlaceSearchAdapter(val context: Context, val searchList: ArrayList<PlaceSearchData>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_search_layout, null)

        /* 위에서 생성된 view를 item.xml 파일의 각 View와 연결하는 과정이다. */
        val searchPhoto = view.findViewById<ImageView>(R.id.itemSearch_Image)
        val searchPlaceName = view.findViewById<TextView>(R.id.itemSearch_PlaceName)
        val searchPlaceAddress = view.findViewById<TextView>(R.id.itemSearch_PlaceAddress)
        val searchBookmark = view.findViewById<ImageView>(R.id.itemSearch_Bookmark)
        val searchX = view.findViewById<TextView>(R.id.itemSearch_X)
        val searchY = view.findViewById<TextView>(R.id.itemSearch_Y)

        /* ArrayList<PlaceSearch>의 변수 dog의 이미지와 데이터를 ImageView와 TextView에 담는다. */
        val search = searchList[position]
        val resourceId =
            context.resources.getIdentifier(search.photo, "drawable", context.packageName)
        val resourceBookmark =
            context.resources.getIdentifier(search.bookmark, "drawable", context.packageName)
        searchPhoto.setImageResource(resourceId)
        searchPlaceName.text = search.placeName
        searchPlaceAddress.text = search.placeAddress
        searchBookmark.setImageResource(resourceBookmark)
        searchX.text = search.search_x
        searchY.text = search.search_y
        return view
    }

    override fun getItem(position: Int): Any {
        return searchList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return searchList.size
    }
}