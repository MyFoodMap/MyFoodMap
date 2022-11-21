package com.example.myfoodmap

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private companion object {
        const val TAG = "회원가입"
        const val REQUEST_FIRST = 1000
    }

    private lateinit var customProgress: CustomProgress
    private var checkOverlapId = false
    private var gender = ""


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val clickNo = getColor(R.color.dark_gray)
        val click = getColor(R.color.gray)

        signUp_Man_Button.setOnClickListener{
            signUp_Woman_Button.setBackgroundColor(click)
            signUp_Man_Button.setBackgroundColor(clickNo)
            gender = "남"
        }
        signUp_Woman_Button.setOnClickListener{
            signUp_Woman_Button.setBackgroundColor(clickNo)
            signUp_Man_Button.setBackgroundColor(click)
            gender = "여"
        }

        signUp_OverlapCheck_Button.setOnClickListener{ checkOverlapId() } //아이디 중복 검사
        signUp_SignUp_Button.setOnClickListener{
            singUp()
            var intent = Intent(this,LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        } //화원가입
    }

    private fun checkOverlapId(){
        val id = signUp_SignUpId_EditText.text.toString()

        if(id.isBlank()){
            startToast("아이디를 입력해주세요")}
        else{
            FireBaseDataBase.getUserData(id,
                mSuccessHandler = { document->
                    if(document != null){
                        checkOverlapId = true
                        startToast("사용할수 있는 아이디입니다")
                    } },
                mFailureHandler = {startToast("이미 존재하는 아이디입니다")})
        }

    }

    private fun singUp() {
        val id = signUp_SignUpId_EditText.text.toString()
        val password = signUp_Password_EditText.text.toString()
        val checkPassword = signUp_PasswordCheck_EditText.text.toString()
        //수정해야함
        val name = "son"
        val nickname = "jansoon"

        val address = signUp_Address_EditText.text.toString()

        if(checkEmpty(id,password,checkPassword,name,gender,address,nickname) && checkOverlapId){
            //로딩창
            showProgressBar()

            //회원 정보 등록
            FireBaseAuth.singUp(id,password,this,
                mSuccessHandler = {uid ->
                    //데이터베이스에 정보 등록
                    FireBaseDataBase.uploadUserData(
                        id, UserInfo(uid,id,name,gender,address,nickname),
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

    //비밀번호 일치 검사
    private fun checkPassword(password: String, checkPassword:String):Boolean{//일치하면 참 아니면 거짓
        if(password == checkPassword) return true
        return false

    }

    //공백 검사
    private fun checkEmpty(
        id:String, password:String,checkPassword: String,
        name:String, gender:String, address:String,nickname: String):Boolean{
        if(id.isBlank()){
            startToast("아이디를 입력하세요")
            return false
        }

        if(password.isBlank()){
            startToast("비밀번호를 입력하세요")
            return false
        }else if(!checkPassword(password,checkPassword)){
            startToast("비밀번호가 일치하지 않습니다")
            return false
        }

        if(name.isBlank()){
            startToast("이름를 입력하세요")
            return false
        }

        if(gender.isBlank()){
            startToast("성별를 입력하세요")
            return false
        }

        if(address.isBlank()){
            startToast("주소를 입력하세요")
            return false
        }

        if(nickname.isBlank()){
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