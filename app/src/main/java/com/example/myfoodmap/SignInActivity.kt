package com.example.myfoodmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private companion object {
        val TAG = "로그인"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signIn_Login_Button.setOnClickListener{
            login()
            val intent = Intent(this,AppMainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun login() {
        val id = signIn_IdEditText.text.toString()
        val pw = signIn_PasswordEditText.text.toString()

        if (id.isNotBlank() && pw.isNotBlank()) {
            FireBaseAuth.signIn(id,pw,this,
                mSuccessHandler = {
                    startToast("로그인에 성공하였습니다.") },
                mFailureHandler = { e ->
                    startToast("로그인에 실패하였습니다")
                    Log.e(TAG, "로그인실패", e) })
        }else startToast("아이디, 비밀번호를 입력해주세요")
    }

    private fun startToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
}