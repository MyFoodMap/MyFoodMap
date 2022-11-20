package com.example.myfoodmap

class UserInfo(uid: String?, id: String, name:String, gender:String, address:String) {
    var uid:String? = uid //Firebase Uid(고유 토큰정보)
    var id:String = id
    var name:String = name
    var gender:String = gender
    var address:String = address

}