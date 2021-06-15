package deu.cse.tos.helper

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import deu.cse.tos.R
import deu.cse.tos.R.id.callHome
import deu.cse.tos.activity.MainActivity
import deu.cse.tos.activity.fragment.*

class BottomNavigationHelper {

    fun enableNavigation(context: Context, view: BottomNavigationView) {
        view.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                callHome -> {
                    val intent = Intent(context, MainActivity::class.java) // 0
                    context.startActivity(intent)
                }
                R.id.callQnA -> {
                    val intent = Intent(context, QnaFragment::class.java) // 0
                    context.startActivity(intent)
                }
                R.id.callBrush -> {
                    val intent = Intent(context, ModeFragment::class.java) // 0
                    context.startActivity(intent)
                }
                R.id.callCalendar -> {
                    val intent = Intent(context, CalendarFragment::class.java) // 0
                    context.startActivity(intent)
                }
                R.id.callInformation -> {
                    val intent = Intent(context, InformationFragment::class.java) // 0
                    context.startActivity(intent)
                }
            }
            false
        }
    }
}