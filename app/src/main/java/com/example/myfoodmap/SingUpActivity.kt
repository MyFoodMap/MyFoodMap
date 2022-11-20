package com.example.myfoodmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_sing_up.*

class SingUpActivity : AppCompatActivity() {

    private companion object {
        const val TAG = "회원가입"
        const val REQUEST_FIRST = 1000
    }

    private lateinit var customProgress: CustomProgress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        customProgress = CustomProgress(this)

        singUp_SingUpButton.setOnClickListener {
            singUp()
        }
    }

    private fun singUp() {
        val id = singUp_ID.text.toString()
        val password = singUp_Password.text.toString()
        val checkPassword = singUp_CheckPassword.text.toString()
        val name = singUp_Name.text.toString()
        val gender = singUp_Gender.text.toString()
        val address = singUp_Address.text.toString()

        if(checkEmpty(id,password,checkPassword,name,gender,address)){

            //로딩창
            showProgressBar()

            //회원 정보 등록
            FireBaseAuth.singUp(id,password,this,
                mSuccessHandler = {uid ->
                    //데이터베이스에 정보 등록
                    FireBaseDataBase.uploadData(
                        "Users", uid,
                        UserInfo(uid,id,name,gender,address),
                        mSuccessHandler = {
                            Log.d(TAG, "회원정보 등록 성공")
                            hideProgressBar() },
                        mFailureHandler = {e -> Log.e(TAG, "회원정보 등록 실패",e)}
                    )

                    startToast("회원 가입에 성공하였습니다.")
                                  },
                mFailureHandler = { e ->
                    Log.w(TAG, "signInWithEmail:failure", e)
                    startToast("회원가입에 실패하였습니다.")
                }
            )
        }
    }

    private fun checkPassword(password: String, checkPassword:String):Boolean{//일치하면 참 아니면 거짓
        if(password == checkPassword) return true
        return false

    }

    private fun checkEmpty(id:String, password:String,checkPassword: String, name:String, gender:String, address:String):Boolean{
        if(id.isEmpty()){
            startToast("아이디를 입력하세요")
            return false
        }

        if(password.isEmpty()){
            startToast("비밀번호를 입력하세요")
            return false
        }else if(!checkPassword(password,checkPassword)){
            startToast("비밀번호가 일치하지 않습니다")
        }

        if(name.isEmpty()){
            startToast("이름를 입력하세요")
            return false
        }

        if(gender.isEmpty()){
            startToast("성별를 입력하세요")
            return false
        }

        if(address.isEmpty()){
            startToast("주소를 입력하세요")
            return false
        }

        return true
    }

    // 프로그레스바 보이기
    private fun showProgressBar() {
        blockLayoutTouch()
        customProgress.show()
    }

    // 프로그레스바 숨기기
    private fun hideProgressBar() {
        clearBlockLayoutTouch()
        customProgress.dismiss()
    }

    // 화면 터치 막기
    private fun blockLayoutTouch() {
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    // 화면 터치 풀기
    private fun clearBlockLayoutTouch() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun startToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }


}