package deu.cse.tos.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import deu.cse.tos.R

class ExitPopupActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_exit_popup)

        val exitButton: Button = findViewById(R.id.btn_exit)
        val stayButton: Button = findViewById(R.id.btn_stay)

        exitButton.setOnClickListener{
            exitApp()
        }
        stayButton.setOnClickListener{
            stayApp()
        }
    }

    override fun onBackPressed() {
        return
    }

    private fun exitApp(){
        setResult(RESULT_OK)
        finish()
    }

    private fun stayApp(){
        setResult(RESULT_CANCELED)
        finish()
    }
}