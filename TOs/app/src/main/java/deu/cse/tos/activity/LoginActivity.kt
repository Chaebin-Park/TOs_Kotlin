package deu.cse.tos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.auth.AuthType
import com.kakao.auth.Session
import com.kakao.util.helper.Utility
import deu.cse.tos.R
import deu.cse.tos.auth.SessionCallback
import kotlinx.android.synthetic.main.activity_login.*

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private var callback: SessionCallback = SessionCallback(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val keyHash = Utility.getKeyHash(this)
        Log.d("HASH", keyHash)

        btn_kakao_login.setOnClickListener{
            kakaoLogin()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)){
            return
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        Session.getCurrentSession().removeCallback(callback)
    }

    private fun kakaoLogin(){
        Session.getCurrentSession().addCallback(callback)
        Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, this)
    }
}