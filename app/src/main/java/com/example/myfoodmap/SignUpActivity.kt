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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.example.myfoodmap.databinding.ActivityKeywordSearchBinding
import com.example.myfoodmap.databinding.ActivitySignUpBinding
import kotlinx.android.synthetic.main.activity_keyword_search.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {

    private companion object {
        const val TAG = "회원가입"
        const val REQUEST_FIRST = 1000
    }

    private lateinit var customProgress: CustomProgress
    private var checkOverlapId = false
    private var gender = ""
    private lateinit var binding : ActivitySignUpBinding

    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK 719ec8dad17c5585c9e25ff8a79fcd96"  // REST API 키
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clickNo = getColor(R.color.dark_gray)
        val click = getColor(R.color.gray)
        customProgress = CustomProgress(this)

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
        } //화원가입
    }

    //아이디중복검사
    private fun checkOverlapId(){
        val id = signUp_SignUpId_EditText.text.toString()

        if(id.isBlank()){
            startToast("아이디를 입력해주세요")}
        else{
            FireBaseDataBase.getUserData(id,
                mSuccessHandler = { document->
                    Log.d(TAG,"아이디 중복 검사 : ${document}")
                    if(document != null){
                        checkOverlapId = true
                        startToast("사용할수 있는 아이디입니다")
                    } },
                mFailureHandler = {startToast("이미 존재하는 아이디입니다")})
        }

    }

    //회원가입
    private fun singUp() {
        val id = signUp_SignUpId_EditText.text.toString()
        val password = signUp_Password_EditText.text.toString()
        val checkPassword = signUp_PasswordCheck_EditText.text.toString()
        val name = signUp_Name_EditText.text.toString()
        val store = Firebase.firestore
        //val address = signUp_SignUpAddress_EditText.text.toString()
        //수정해야함
        val nickname = "jansoon"
        val address = "광운대"

        if(checkEmpty(id,password,checkPassword,name,gender,address,nickname) && checkOverlapId){
            //로딩창
            showProgressBar()
            //회원 정보 등록
            FireBaseAuth.singUp(id,password,this,
                mSuccessHandler = {uid ->
                    startToast("데이터베이스 정보 저장 시작")
                    //데이터베이스에 정보 등록
                    FireBaseDataBase.uploadUserData(
                        id, UserInfo(uid,id,name,gender,address,nickname),
                        mSuccessHandlerUser = {
                            Log.d(TAG, "회원정보 등록 성공")
                            FireBaseAuth.auth.signOut()
                            hideProgressBar()
                            gotoLogin()},
                        mFailureHandlerUser = {e -> Log.e(TAG, "회원정보 등록 실패",e)}
                    )
                },
                mFailureHandler = { e ->
                    Log.w(TAG, "signInWithEmail:failure", e)
                    startToast("회원가입에 실패하였습니다.")
                }
            )
        }
        binding.signUpAddressSearchButton.setOnClickListener() {
            var etTextKeyword=signUp_SignUpAddress_EditText.text.toString()
            signUp_AddressSearch_EditText.setText(etTextKeyword)
            searchKeyword(etTextKeyword)
            signUp_SearchScroll.visibility= View.VISIBLE
            signUp_SearchScrollBackground.visibility= View.VISIBLE
        }
        binding.signUpAddressSearchButton2.setOnClickListener() {
            var etTextKeyword2=signUp_AddressSearch_EditText.text.toString()
            searchKeyword(etTextKeyword2)
        }
        signUp_AddressSearchResult1.setOnClickListener() {
            signUp_SearchScroll.visibility= View.INVISIBLE
            signUp_SearchScrollBackground.visibility= View.INVISIBLE
            signUp_SignUpAddress_EditText.setText(placeName1.text)
        }
        signUp_AddressSearchResult2.setOnClickListener() {
            signUp_SearchScroll.visibility= View.INVISIBLE
            signUp_SearchScrollBackground.visibility= View.INVISIBLE
            signUp_SignUpAddress_EditText.setText(placeName2.text)
        }
        signUp_AddressSearchResult3.setOnClickListener() {
            signUp_SearchScroll.visibility= View.INVISIBLE
            signUp_SearchScrollBackground.visibility= View.INVISIBLE
            signUp_SignUpAddress_EditText.setText(placeName3.text)
        }
        signUp_AddressSearchResult4.setOnClickListener() {
            signUp_SearchScroll.visibility= View.INVISIBLE
            signUp_SearchScrollBackground.visibility= View.INVISIBLE
            signUp_SignUpAddress_EditText.setText(placeName4.text)
        }
        signUp_AddressSearchResult5.setOnClickListener() {
            signUp_SearchScroll.visibility= View.INVISIBLE
            signUp_SearchScrollBackground.visibility= View.INVISIBLE
            signUp_SignUpAddress_EditText.setText(placeName5.text)
        }
    }

    // 키워드 검색 함수
    private fun searchKeyword(keyword: String) {
        val retrofit = Retrofit.Builder()   // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPI::class.java)   // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(API_KEY, keyword)   // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<KakaoData> {
            override fun onResponse(
                call: Call<KakaoData>,
                response: Response<KakaoData>
            ) {
                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                Log.d("Test", "${response.body()}")
                response.body()?.let {
                    for(index in 0 until it.documents.size) { // 키워드 검색으로 나온 데이터 출력
                        Log.d("Address", "${it.documents[index].place_name}") // 장소
                        Log.d("Address", "${it.documents[index].address_name}") // 주소
                        Log.d("Address", "${it.documents[index].x}") // 경도
                        Log.d("Address", "${it.documents[index].y}") // 위도
                        when(index) {
                            0 -> {
                                placeName1.text="${it.documents[index].place_name}"
                                addressName1.text="${it.documents[index].address_name}"
                            } 1 -> {
                                placeName2.text="${it.documents[index].place_name}"
                                addressName2.text="${it.documents[index].address_name}"
                            } 2 -> {
                                placeName3.text="${it.documents[index].place_name}"
                                addressName3.text="${it.documents[index].address_name}"
                            } 3 -> {
                                placeName4.text="${it.documents[index].place_name}"
                                addressName4.text="${it.documents[index].address_name}"
                            } 4 -> {
                                placeName5.text="${it.documents[index].place_name}"
                                addressName5.text="${it.documents[index].address_name}"
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<KakaoData>, t: Throwable) {
                // 통신 실패
                Log.w("MainActivity", "통신 실패: ${t.message}")
            }
        })
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

   private fun gotoLogin(){
       var intent = Intent(this,LoginActivity::class.java)
       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
       startActivity(intent)
       finish()
   }

    private fun startToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
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

}