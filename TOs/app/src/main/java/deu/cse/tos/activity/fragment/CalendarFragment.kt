package deu.cse.tos.activity.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.*
import deu.cse.tos.R
import deu.cse.tos.data.LoginData
import deu.cse.tos.data.ToothInfoDTO
import deu.cse.tos.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CalendarFragment : Fragment() {

    private val input: HashMap<String, Any> = HashMap()
    private val formatTime = SimpleDateFormat("EEE", Locale.KOREAN)
    private lateinit var loginData: LoginData
    private lateinit var bundle: Bundle

    private var state = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_calendar, container, false)

        rootView.calendarView.setSelectedDate(CalendarDay.today())
        rootView.calendarView.addDecorator(TodayDecorator())

        if(!state){
            state = true
            rootView.calendarView.selectedDate = CalendarDay.today()
            rootView.calendarView.addDecorator(TodayDecorator())
            updateCalendar(CalendarDay.today(), rootView)
        }

        rootView.calendarView.setOnDateChangedListener { widget, date, selected ->
            updateCalendar(date, rootView)
        }

        bundle = requireArguments()
        loginData = bundle.getSerializable("LOGIN") as LoginData

        return rootView
    }


    private fun updateCalendar(date: CalendarDay, v: View){
        val dow = date.date.format(DateTimeFormatter.ofPattern("EEE", Locale.KOREAN)) + "요일"
        val d = date.date.format(DateTimeFormatter.ofPattern("YYYY년 MM월 dd일"))
        v.tvCalendarDow.text = dow
        v.tvCalendarDate.text = d

        input.put("hash_key", R.string.kakao_native_app_key)
        input.put("select_date", date.date.toString())

        RetrofitBuilder.api.postCalendarResult(input).enqueue(object : Callback<ToothInfoDTO>{
            override fun onResponse(call: Call<ToothInfoDTO>, response: Response<ToothInfoDTO>) {
                if(response.isSuccessful){
                    val data = response.body()!!
                    setCalendar(data, v)
                }
            }

            override fun onFailure(call: Call<ToothInfoDTO>, t: Throwable) {
                log("통신 실패 : $t")
            }

        })

    }

    private fun setCalendar(data: ToothInfoDTO, v: View){
        if (data.morningTime != "0"){
            v.ivCalendarMorningStatus.setImageResource(returnToothResBig(data.morningCount))
            v.ivCalendarMorningFace.setImageResource(returnToothResSmall(data.morningCount))
            v.tvCalendarMorningTime.text = data.morningTime
        } else if (data.morningTime == "0"){
            v.ivCalendarMorningStatus.setImageResource(R.drawable.group_254)
            v.ivCalendarMorningFace.setImageResource(R.drawable.basic_off_close)
            v.tvCalendarMorningTime.text = defaultTime
        }

        if (data.afternoonTime != "0"){
            v.ivCalendarAfternoonStatus.setImageResource(returnToothResBig(data.afternoonCount))
            v.ivCalendarAfternoonFace.setImageResource(returnToothResSmall(data.afternoonCount))
            v.tvCalendarAfternoonTime.text = data.afternoonTime
        } else if (data.afternoonTime == "0"){
            v.ivCalendarAfternoonStatus.setImageResource(R.drawable.group_254)
            v.ivCalendarAfternoonFace.setImageResource(R.drawable.basic_off_close)
            v.tvCalendarAfternoonTime.text = defaultTime
        }

        if (data.dinnerTime != "0"){
            v.ivCalendarDinnerStatus.setImageResource(returnToothResBig(data.dinnerCount))
            v.ivCalendarDinnerFace.setImageResource(returnToothResSmall(data.dinnerCount))
            v.tvCalendarDinnerTime.text = data.dinnerTime
        } else if (data.dinnerTime == "0"){
            v.ivCalendarDinnerStatus.setImageResource(R.drawable.group_254)
            v.ivCalendarDinnerFace.setImageResource(R.drawable.basic_off_close)
            v.tvCalendarDinnerTime.text = defaultTime
        }

        if (data.nightTime != "0"){
            v.ivCalendarNightStatus.setImageResource(returnToothResBig(data.nightCount))
            v.ivCalendarNightFace.setImageResource(returnToothResSmall(data.nightCount))
            v.tvCalendarNightTime.text = data.nightTime
        } else if (data.nightTime == "0"){
            v.ivCalendarNightStatus.setImageResource(R.drawable.group_254)
            v.ivCalendarNightFace.setImageResource(R.drawable.basic_off_close)
            v. tvCalendarNightTime.text = defaultTime
        }
    }

    private fun returnToothResBig(count: Int): Int {
        return when(count){
            0 -> R.drawable.group_252    // 건강해요
            1 -> R.drawable.group_257    // 치아가 아파요
            else -> R.drawable.group_253 // 출혈이 있어요
        }
    }

    private fun returnToothResSmall(count: Int): Int {
        return when(count){
            0 -> R.drawable.happy
            1 -> R.drawable.soso
            else -> R.drawable.sad
        }
    }

    private fun log(msg: String){
        Log.e("CalendarFragment", msg)
    }

    inner class TodayDecorator: DayViewDecorator {
        private val today = CalendarDay.today()

        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return today == day
        }

        override fun decorate(view: DayViewFacade?) {
            Log.e(TAG, "TodayDecorator.decorate")

        }
    }

    companion object {
        var INIT_STATE = false
        const val defaultTime = "--:--"
        val TAG = CalendarFragment.toString()
    }
}