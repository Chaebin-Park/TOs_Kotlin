package deu.cse.tos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import deu.cse.tos.R
import deu.cse.tos.data.LoginData
import kotlinx.android.synthetic.main.activity_self_check.*
import kotlin.math.log

class SelfCheckActivity : AppCompatActivity() {
    private lateinit var mIntent: Intent
    private lateinit var loginData: LoginData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_check)

        loginData = intent.getSerializableExtra("LOGIN") as LoginData

        tv_self_check_name.text = loginData.name

        btn_submission.setOnClickListener{
            mIntent = Intent(this, CheckResultActivity::class.java)
            mIntent.putExtra("LOGIN", loginData)
            finish()
            startActivity(mIntent)
        }
    }
}