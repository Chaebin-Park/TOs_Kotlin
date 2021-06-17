package deu.cse.tos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import deu.cse.tos.R
import deu.cse.tos.data.LoginData
import kotlinx.android.synthetic.main.activity_check_result.*

class CheckResultActivity : AppCompatActivity() {
    private lateinit var loginData: LoginData
    private lateinit var mIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_result)

        loginData = intent.getSerializableExtra("LOGIN") as LoginData

        tv_result_name.text = loginData.name

        btn_check_result_ok.setOnClickListener{
            finish()
        }
    }
}