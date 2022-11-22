package com.example.myfoodmap

import android.net.Uri

class UserInfo( uid: String?, id: String,
                name:String, gender:String, nickname: String,
                place:String, address:String, x:String,y:String,
                 photoUri: Uri? = null) {
    var uid:String? = uid //Firebase Uid(고유 토큰정보)
    var id:String = id
    var name:String = name
    var gender:String = gender
    var place:String = place
    var address:String = address
    var x:String = x
    var y:String = y
    var nickname:String = nickname
    var photoUri:Uri? = photoUri

}