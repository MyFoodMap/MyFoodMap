package com.example.myfoodmap

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class FireBaseStorage {
    private val storage = Firebase.storage



    private fun downloadImageToFirestroage(filename:String,
                                           mSuccessHandler:() -> Unit,
                                           mFailureHandler:(java.lang.Exception) -> Unit,) {
        val addOnFailureListener = storage.reference
            .child("Users/photo").child(filename).downloadUrl
            .addOnSuccessListener {
                mSuccessHandler()
            }
            .addOnFailureListener { e->
                mFailureHandler(e)
            }
    }
}