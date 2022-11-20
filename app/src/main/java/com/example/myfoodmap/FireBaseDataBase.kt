package com.example.myfoodmap

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FireBaseDataBase {
    private val store by lazy {Firebase.firestore}

    fun < T: Any> uploadData(
        collection: String, document:String, data: T,
        mSuccessHandler:() -> Unit,
        mFailureHandler:(java.lang.Exception) -> Unit,
    ) {
        store.collection(collection).document(document).set(data)
            .addOnSuccessListener {
                mSuccessHandler()
            }
            .addOnFailureListener { e ->
                mFailureHandler(e)
            }
    }
}