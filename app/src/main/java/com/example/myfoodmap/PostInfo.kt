package com.example.myfoodmap

import java.io.Serializable

class PostInfo (restaurantName:String = "",
               tasteEvaluation:String="", costEvaluation:String="",cleanlinessEvaluation:String="",
               totalEvalaution:String = "", oneLineComment:String="",
                address: String="",x:String = "",y:String = "",
                imageUri:String = "", time:Long = 0) : Serializable {
    var restaurantName:String = restaurantName
    var tasteEvaluation:String = tasteEvaluation
    var costEvaluation:String = costEvaluation
    var cleanlinessEvaluation:String = cleanlinessEvaluation
    var totalEvalaution:String = totalEvalaution
    var oneLineComment:String = oneLineComment
    var address:String = address
    var x:String = x
    var y:String = y
    var time:Long = time
    var imageUri:String = imageUri
    //var imageUris:ArrayList<Uri> = ArrayList()

    fun set( oneLineComment:String=""){
        this.time = System.currentTimeMillis()
        this.oneLineComment = oneLineComment
    }

    fun evaluationSet(tasteEvaluation:String, costEvaluation:String,cleanlinessEvaluation:String,
                      totalEvalaution:String){
        this.tasteEvaluation = tasteEvaluation
        this.costEvaluation = costEvaluation
        this.cleanlinessEvaluation = cleanlinessEvaluation
        this.totalEvalaution = totalEvalaution
    }

    fun placeSet(restaurantName:String,address: String,x:String ,y:String ){
        this.restaurantName = restaurantName
        this.address= address
        this.x = x
        this.y = y
    }
}