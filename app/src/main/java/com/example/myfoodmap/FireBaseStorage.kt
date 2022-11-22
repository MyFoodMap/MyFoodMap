package com.example.myfoodmap

import android.net.Uri
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

object FireBaseStorage {
    private val storage = Firebase.storage

    fun uploadPostingImage(uid:String, restaurantName:String, imageURI: Uri,
                                   mSuccessHandler:() -> Unit,
                                   mFailureHandler:() -> Unit,
                                   mFailureHandlerException:(java.lang.Exception) -> Unit) {

        Log.d("사진등록","${imageURI}")
        val uploadFile = storage.reference.child("${uid}/PostingPhotos/${restaurantName}")

        uploadFile.child("${restaurantName}.png").putFile(imageURI)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    mSuccessHandler()
                } else {
                    mFailureHandler()
                }
            }.addOnFailureListener{
                e -> mFailureHandlerException(e)
            }
        /*
        사진 여러개일때
        var photoNum = 1
        for(photoUri in imageURIList){
            uploadFile.child("${photoNum++}.png").putFile(photoUri)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        startToast("사진 저장 성공했습니다")
                    } else {
                        startToast("사진 저장에 실패했습니다")
                    }
                }
        }
        */

    }

    fun downloadImageUri(uid:String, restaurantName:String,
                                 mSuccessHandler:(Uri) -> Unit,
                                 mFailureHandler:(java.lang.Exception) -> Unit, ) {

        var downloadUris= storage.reference.child("${uid}/PostingPhotos/${restaurantName}")

        downloadUris.child("${restaurantName}.png").downloadUrl
            .addOnSuccessListener { photoUri->
                mSuccessHandler(photoUri)
                //postInfo.imageUris.add(photoUri)
                //Log.i(TAG,"사진 다운로드 성공")
                //startToast("사진 저장에 성공했습니다")
            }
            .addOnFailureListener { e->
                mFailureHandler(e)
                //startToast("사진 다운로드에 실패했습니다")
                //Log.e(TAG,"사진 다운로드 실패",it)
            }

        /*
        사진 여러개 일떄//photoCount:Int 변수 필요
        var downloadUris= storage.reference.child("${uid}/PostingPhotos/${restaurantName}")
        for(i in 1 .. photoCount) {
            downloadUris.child("${i}.png").downloadUrl
                .addOnSuccessListener { photoUri->
                    postInfo.imageUris.add(photoUri)
                    Log.i(TAG,"사진 다운로드 성공")
                    startToast("사진 저장에 성공했습니다")
                }
                .addOnFailureListener {
                    startToast("사진 다운로드에 실패했습니다")
                    Log.e(TAG,"사진 다운로드 실패",it)
                }
        }*/

    }


}