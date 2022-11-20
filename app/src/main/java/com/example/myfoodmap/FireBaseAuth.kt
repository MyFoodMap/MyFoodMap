package com.example.myfoodmap

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object FireBaseAuth {
    private val auth by lazy { Firebase.auth}

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

}