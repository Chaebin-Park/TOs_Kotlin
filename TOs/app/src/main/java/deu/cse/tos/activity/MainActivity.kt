package deu.cse.tos.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import deu.cse.tos.R
import deu.cse.tos.activity.fragment.*
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var bottomNavigationView: BottomNavigationView
    private var qnaFragment= QnaFragment()
    private var mainFragment = MainFragment()
    private var informationFragment = InformationFragment()
    private var calendarFragment = CalendarFragment()
    private var modeFragment = ModeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_layout, mainFragment).commitAllowingStateLoss()
        val intent = Intent(this, ModeActivity::class.java)

        navigation.setOnNavigationItemReselectedListener { item ->
            fragmentTransaction = fragmentManager.beginTransaction()
            when(item.itemId){
                R.id.callHome -> {
                    fragmentTransaction.replace(R.id.main_layout, mainFragment).commitAllowingStateLoss()
                }
                R.id.callQnA -> {
                    fragmentTransaction.replace(R.id.main_layout, qnaFragment).commitAllowingStateLoss()
                }
                R.id.callInformation -> {
                    fragmentTransaction.replace(R.id.main_layout, calendarFragment).commitAllowingStateLoss()
                }
                R.id.callBrush -> {
                    fragmentTransaction.replace(R.id.main_layout, modeFragment).commitAllowingStateLoss()
                }
                else -> {
                    Log.e("NAV_ERR", "YOU CANT TOUCH THIS")
                }
            }
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

    private fun mPopupClick(){
        val intent = Intent(this, ExitPopupActivity::class.java)
        startActivityForResult(intent, BACK_PRESSED)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_layout, fragment).commit()
    }

    companion object {
        const val ACTIVITY_NUM = 0
        const val BACK_PRESSED = 1
        const val TAG = "MainActivity"
    }
}