package com.example.myfoodmap

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object FireBaseAuth {
    val auth by lazy { Firebase.auth}
    var user:FirebaseUser? = null

    init{
        val currentUser = auth.currentUser
        if(currentUser != null){
            signOut()
        }
    }

    fun singUp(email:String, password:String,
                      activity: AppCompatActivity,
                      mSuccessHandler:(String) -> Unit,
                      mFailureHandler:(java.lang.Exception) -> Unit,
    ){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity){ task->
                //회원가입 성공시
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser? = auth.currentUser
                    //데이터베이스에 정보 저장
                    if (firebaseUser != null) {
                        mSuccessHandler(firebaseUser.uid)
                    }
                } else {
                    task.exception?.let { mFailureHandler(it) }
                }
            }
    }


    fun signIn(email:String, password:String,
               activity: AppCompatActivity,
               mSuccessHandler:() -> Unit,
               mFailureHandler:(java.lang.Exception?) -> Unit
    ){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    if (auth.currentUser != null) {
                        user = auth.currentUser
                        mSuccessHandler()
                    } else {
                        mFailureHandler(task.exception)
                    }
                }
            }
    }

    private fun signOut(){
        auth.signOut()
    }


}