package com.example.myfoodmap

import com.google.firebase.firestore.*
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
        val user = store.collection("Users").document(userEmail)
        user.set(userInfo)
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
            .addOnSuccessListener { mSuccessHandlerUser() }
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
            .addOnSuccessListener { document-> mSuccessHandlerPost(document) }
            .addOnFailureListener { exception -> mFailureHandlerPost(exception) }
    }


    fun getPostingDataForUser(
        userEmail:String,
        mSuccessHandler:(QuerySnapshot) -> Unit,
        mFailureHandler:(Exception) -> Unit){

        val userPost = store.collection("Users").document(userEmail).collection("UserPosting")

       userPost.get()
            .addOnSuccessListener { result-> mSuccessHandler(result) }
            .addOnFailureListener { exception -> mFailureHandler(exception) }
    }

    fun loadBookMark(userEmail:String?,
                     mSuccessHandler:(DocumentSnapshot) -> Unit,
                     mFailureHandler:(Exception) -> Unit){
        val bookMark = store.collection("Users").document(userEmail!!)
            .collection("UserPosting").document("BookMark")
        bookMark.get().addOnSuccessListener{ document-> mSuccessHandler(document) }
            .addOnFailureListener{e-> mFailureHandler(e)}

    }

    fun addBookMark( userEmail:String?,
                     restaurantName:String, x:String, y:String,
                     mSuccessHandler:() -> Unit,
                     mFailureHandler:(Exception) -> Unit){
        val bookMark = store.collection("Users").document(userEmail!!)
            .collection("UserPosting").document("BookMark")

        val pair = hashMapOf("x" to x, "y" to y , "time" to System.currentTimeMillis())
        val update = hashMapOf<String,Any>(restaurantName to pair)

        bookMark.get().addOnCompleteListener{ task->
            if(task.isSuccessful){
                val document = task.result
                if(document.exists()) {
                    bookMark.update(update)
                        .addOnSuccessListener { mSuccessHandler() }
                        .addOnFailureListener { e -> mFailureHandler(e) }
                }else{
                    bookMark.set(update)
                        .addOnSuccessListener { mSuccessHandler() }
                        .addOnFailureListener { e -> mFailureHandler(e)}
                }
            }
        }


    }

    fun delBookMark( userEmail:String?, restaurantName:String,
                     mSuccessHandler:() -> Unit,
                     mFailureHandler:(Exception) -> Unit){

        val bookMark = store.collection("Users").document(userEmail!!).collection("UserPosting").document("BookMark")
        val update = hashMapOf<String,Any>(restaurantName to FieldValue.delete())

        bookMark.update(update)
            .addOnSuccessListener{
                mSuccessHandler()
            }.addOnFailureListener{ e->
                mFailureHandler(e)
            }

    }






}