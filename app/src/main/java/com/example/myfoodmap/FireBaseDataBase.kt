package com.example.myfoodmap

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.core.UserData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

object FireBaseDataBase {
    private val store by lazy {Firebase.firestore}

    fun uploadUserData(
        userEmail:String, userInfo: UserInfo,
        mSuccessHandler:() -> Unit,
        mFailureHandler:(Exception) -> Unit,
    ) {
        store.collection("Users").document(userEmail).set(userInfo)
            .addOnSuccessListener {
                mSuccessHandler()
            }
            .addOnFailureListener { e ->
                mFailureHandler(e)
            }
    }

    fun getUserData(
        userEmail:String,
        mSuccessHandler:(DocumentSnapshot) -> Unit,
        mFailureHandler:(Exception) -> Unit,){
        store.collection("Users").document(userEmail).get()
            .addOnSuccessListener { document->
                mSuccessHandler(document)
            }
            .addOnFailureListener { exception ->
                mFailureHandler(exception)
            }
    }

    fun < T: Any> uploadData(
        collection: String, document:String, data: T,
        mSuccessHandler:() -> Unit,
        mFailureHandler:(Exception) -> Unit,
    ) {
        store.collection(collection).document(document).set(data)
            .addOnSuccessListener {
                mSuccessHandler()
            }
            .addOnFailureListener { e ->
                mFailureHandler(e)
            }
    }

    fun getData(
        collection: String, document:String,
        mSuccessHandler:(DocumentSnapshot) -> Unit,
        mFailureHandler:(Exception) -> Unit,){
        store.collection(collection).document(document).get()
            .addOnSuccessListener { document->
                mSuccessHandler(document)
            }
            .addOnFailureListener { exception ->
                mFailureHandler(exception)
            }
    }
}