package com.example.myfoodmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private companion object {
        val TAG = "로그인엑티비티"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signIn_Login_Button.setOnClickListener{
            login()
        }
    }

    private fun login() {
        val id = signIn_IdEditText.text.toString()
        val pw = signIn_PasswordEditText.text.toString()

        if (id.isNotBlank() && pw.isNotBlank()) {
            FireBaseAuth.signIn(id,pw,this,
                mSuccessHandler = {
                    FireBaseDataBase.getUserData(id,
                        mSuccessHandler = { doucment ->
                            startToast("로그인에 성공하였습니다.")
                            gotoAppMain(doucment.toObject<UserInfo>()) },
                        mFailureHandler = {e->
                            startToast("로그인에 실패하였습니다: 정보가 없습니다")
                            Log.e(TAG, "로그인실패: 정보 없음 :", e)})

                     },
                mFailureHandler = { e ->
                    startToast("로그인에 실패하였습니다")
                    Log.e(TAG, "로그인실패", e) })
        }else startToast("아이디, 비밀번호를 입력해주세요")
    }

    private fun gotoAppMain(userInfo: UserInfo?){
        val intent = Intent(this,AppMainActivity::class.java)
        intent.putExtra("user",userInfo)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    private fun startToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
}