package deu.cse.tos.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import deu.cse.tos.R
import deu.cse.tos.activity.fragment.*
import deu.cse.tos.data.LoginData
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val fragmentMain: MainFragment = MainFragment()
    private val fragmentInfo: InformationFragment = InformationFragment()
    private val fragmentMode: ModeFragment = ModeFragment()
    private val fragmentQna: QnaFragment = QnaFragment()
    private val fragmentCalendar: CalendarFragment = CalendarFragment()
    private val fragmentManager = supportFragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var loginData: LoginData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentTransaction = fragmentManager.beginTransaction()

        val intent = intent
        loginData = intent.getSerializableExtra("LOGIN") as LoginData
        val bundle = Bundle()
        bundle.putSerializable("LOGIN", loginData)

        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_layout, fragmentMain).commitAllowingStateLoss()
        fragmentMain.arguments = bundle

        navigation.setOnNavigationItemSelectedListener { item: MenuItem ->
            fragmentTransaction = fragmentManager.beginTransaction()
            when (item.itemId) {
                R.id.callHome -> {
//                    replaceFragment(fragmentMain)
                    fragmentTransaction.replace(R.id.main_layout, fragmentMain).commitAllowingStateLoss()
                    fragmentMain.arguments = bundle
                }
                R.id.callQnA -> {
//                    replaceFragment(fragmentQna)
                    fragmentTransaction.replace(R.id.main_layout, fragmentQna).commitAllowingStateLoss()
                    fragmentQna.arguments = bundle
                }
                R.id.callCalendar -> {
//                    replaceFragment(fragmentCalendar)
                    fragmentTransaction.replace(R.id.main_layout, fragmentCalendar).commitAllowingStateLoss()
                    fragmentCalendar.arguments = bundle

                }
                R.id.callInformation -> {
//                    replaceFragment(fragmentInfo)
                    fragmentTransaction.replace(R.id.main_layout, fragmentInfo).commitAllowingStateLoss()
                    fragmentInfo.arguments = bundle
                }
                R.id.callBrush -> {
//                    replaceFragment(fragmentMode)
                    fragmentTransaction.replace(R.id.main_layout, fragmentMode).commitAllowingStateLoss()
                    fragmentMode.arguments = bundle
                }
            }
            false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == BACK_PRESSED){
            if(resultCode == RESULT_OK){
                moveTaskToBack(true)
                finish()
                android.os.Process.killProcess(android.os.Process.myPid())
            }
            else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this, "stay", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        mPopupClick()
    }

    fun replaceFragment(fragment: Fragment){
        val bundle = Bundle()
        bundle.putSerializable("LOGIN", loginData)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_layout, fragment).commit()
        fragment.arguments = bundle
    }

    private fun mPopupClick(){
        val intent = Intent(this, ExitPopupActivity::class.java)
        startActivityForResult(intent, BACK_PRESSED)
    }

    companion object {
        const val ACTIVITY_NUM = 0
        const val BACK_PRESSED = 1
        const val TAG = "MainActivity"
    }
}