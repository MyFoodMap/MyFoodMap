package com.example.myfoodmap

import android.net.Uri
import androidx.core.net.toUri
import java.io.Serializable

class PostInfo (restaurantName:String = "",
               tasteEvaluation:String="", costEvaluation:String="",cleanlinessEvaluation:String="",
               totalEvalaution:String = "",
               oneLineComment:String="", address: String="") : Serializable {
    var restaurantName:String = restaurantName
    var tasteEvaluation:String = tasteEvaluation
    var costEvaluation:String = costEvaluation
    var cleanlinessEvaluation:String = cleanlinessEvaluation
    var totalEvalaution:String = totalEvalaution
    var oneLineComment:String = oneLineComment
    var address:String = address
    var imageUri:String = ""
    //var imageUris:ArrayList<Uri> = ArrayList()

    fun set(restaurantName:String,
            tasteEvaluation:String="", costEvaluation:String="",cleanlinessEvaluation:String="",
            totalEvalaution:String = "",
            oneLineComment:String="", address: String=""){
        this.restaurantName = restaurantName
        this.tasteEvaluation = tasteEvaluation
        this.costEvaluation = costEvaluation
        this.cleanlinessEvaluation = cleanlinessEvaluation
        this.totalEvalaution = totalEvalaution
        this.oneLineComment = oneLineComment
        this.address = address
    }

}