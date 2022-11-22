package com.example.myfoodmap

import android.net.Uri

class PostInfo(restaurantName:String,
               tasteEvaluation:String="", costEvaluation:String="",cleanlinessEvaluation:String="",
               oneLineComment:String="", address: String="") {
    var restaurantName:String = restaurantName
    var tasteEvaluation:String = tasteEvaluation
    var costEvaluation:String = costEvaluation
    var cleanlinessEvaluation:String = cleanlinessEvaluation
    var oneLineComment:String = oneLineComment
    var address:String = address
    var imageUri:Uri? = null
    //var imageUris:ArrayList<Uri> = ArrayList()

    fun isEmpty():Boolean{
        return restaurantName.isBlank()
                && tasteEvaluation.isBlank() && costEvaluation.isBlank() && cleanlinessEvaluation.isBlank()
                && oneLineComment.isBlank() && address.isBlank()
                && imageUri != null
    }

    fun saveEvaluation(taste:Double,cost:Double, cleanliness:Double){
        tasteEvaluation = taste.toString()
        costEvaluation = cost.toString()
        cleanlinessEvaluation = cleanliness.toString()
    }
}