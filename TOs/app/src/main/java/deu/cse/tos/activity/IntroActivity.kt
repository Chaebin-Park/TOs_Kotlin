package deu.cse.tos.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import deu.cse.tos.R

@Suppress("DEPRECATION")
class IntroActivity : AppCompatActivity() {

    val handler = Handler()
    var runnable = Runnable {
        // 4초뒤에 다음화면(MainActivity)으로 넘어가기 Handler 사용
        val intentLogin = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intentLogin) // 다음화면으로 넘어가기
        finish() // Activity 화면 제거
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = 0x00000000 // transparent
        val decorView = getWindow().decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }

    override fun onResume() {
        super.onResume() // 다시 화면에 들어어왔을 때 예약 걸어주기
        handler.postDelayed(runnable, 2000) // 4초 뒤에 Runnable 객체 수행
    }

    override fun onPause() {
        super.onPause() // 화면을 벗어나면, handler 에 예약해놓은 작업을 취소하자
        handler.removeCallbacks(runnable) // 예약 취소
    }
}