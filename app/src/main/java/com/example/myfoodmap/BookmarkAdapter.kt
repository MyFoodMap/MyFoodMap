package com.example.myfoodmap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class BookmarkAdapter(val context: Context, val bookmarkList: ArrayList<BookmarkData>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */
        val bookmarkView: View = LayoutInflater.from(context).inflate(R.layout.item_bookmark_layout, null)

        /* 위에서 생성된 view를 item.xml 파일의 각 View와 연결하는 과정이다. */
        val bookmarkImg = bookmarkView.findViewById<ImageView>(R.id.itemBookmark_Img)
        val bookmarkBookmarkName = bookmarkView.findViewById<TextView>(R.id.itemBookmark_BookmarkName)
        val bookmarkBookmark = bookmarkView.findViewById<ImageView>(R.id.itemBookmark_Bookmark)

        /* ArrayList<PlaceSearch>의 변수 bookmark의 이미지와 데이터를 ImageView와 TextView에 담는다. */
        val bookmark = bookmarkList[position]
        val resourceImg =
            context.resources.getIdentifier(bookmark.img, "drawable", context.packageName)
        val resourceBookmark =
            context.resources.getIdentifier(bookmark.bookmark, "drawable", context.packageName)
        bookmarkImg.setImageResource(resourceImg)
        bookmarkBookmarkName.text = bookmark.bookmarkName
        bookmarkBookmark.setImageResource(resourceBookmark)

        return bookmarkView
    }

    override fun getItem(position: Int): Any {
        return bookmarkList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return bookmarkList.size
    }
}