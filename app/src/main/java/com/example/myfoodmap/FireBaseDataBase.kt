package com.example.myfoodmap

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.core.UserData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

object FireBaseDataBase {
    private val store = Firebase.firestore

    fun uploadUserData(
        userEmail:String, userInfo: UserInfo,
        mSuccessHandlerUser:() -> Unit,
        mFailureHandlerUser:(Exception) -> Unit,
    ) {
        store.collection("Users").document(userEmail).set(userInfo)
            .addOnSuccessListener {
                mSuccessHandlerUser()
            }
            .addOnFailureListener { e ->
                mFailureHandlerUser(e)
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

    fun uploadPostingData(
        uid: String, userEmail:String, post:PostInfo,
        mSuccessHandlerUser:() -> Unit,
        mFailureHandlerUser:(Exception) -> Unit,
        mSuccessHandlerPost:() -> Unit,
        mFailureHandlerPost:(Exception) -> Unit
    ) {
        val userPost = store.collection("Users").document(userEmail).collection("UserPosting").document(post.restaurantName)
        val restaurantPost = store.collection("Posts").document(post.restaurantName)
            .collection("Posting").document("Users")

        //개인 사용자 폴더에 저장
        userPost.set(post)
            .addOnSuccessListener {
                mSuccessHandlerUser()

            }
            .addOnFailureListener { e -> mFailureHandlerUser(e)/**/}

        //식당 정보 폴더에 저장
        restaurantPost.set(hashMapOf(userEmail to uid), SetOptions.merge())
            .addOnSuccessListener {
                mSuccessHandlerPost()
            }
            .addOnFailureListener { e -> mFailureHandlerPost(e) }
    }

    fun getPostingDataForRestaurant(
        post:PostInfo,
        mSuccessHandlerPost:(DocumentSnapshot) -> Unit,
        mFailureHandlerPost:(Exception) -> Unit){

        val restaurantPost = store.collection("Posts").document(post.restaurantName)
            .collection("Posting").document("Users")

       restaurantPost.get()
            .addOnSuccessListener { document->
                mSuccessHandlerPost(document)
            }
            .addOnFailureListener { exception ->
                mFailureHandlerPost(exception)
            }
    }

    /*
    fun getPostingDataForUser(
        uid: String, userEmail:String, post:PostInfo,
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


    */

}