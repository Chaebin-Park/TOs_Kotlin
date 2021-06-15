package deu.cse.tos.activity.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import deu.cse.tos.Data.ToothInfoDTO
import deu.cse.tos.Data.UserAccount
import deu.cse.tos.R
import deu.cse.tos.retrofit.RetrofitCalendarInterface
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CalendarFragment : Fragment() {

    private lateinit var retrofit: Retrofit
    private lateinit var retrofitCalendarInterface: RetrofitCalendarInterface
    private val input: HashMap<String, Any> = HashMap()
    private val formatTime = SimpleDateFormat("EEE", Locale.KOREAN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitCalendarInterface = retrofit.create(RetrofitCalendarInterface::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }


    private fun updateCalendar(date: CalendarDay){
        calendarView.selectedDate = CalendarDay.today()
        calendarView.addDecorator(TodayDecorator())
        val dow = date.date.format(DateTimeFormatter.ofPattern("EEE", Locale.KOREAN)) + "요일"
        val d = date.date.format(DateTimeFormatter.ofPattern("YYYY년 MM월 dd일"))
        tvCalendarDow.text = dow
        tvCalendarDate.text = d

        UserAccount.instance.hashKey?.let { input.put("hashKey", it) }
        input["selectDate"] = date.date.toString()

        retrofitCalendarInterface.postCalendarResult(input)
            ?.enqueue(object : Callback<ToothInfoDTO?> {
                override fun onResponse(call: Call<ToothInfoDTO?>, response: Response<ToothInfoDTO?>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (!data!!.morningTime.equals("0")) {
                            ivCalendarMorningStatus.setImageResource(returnToothResBig(data.morningCount))
                            ivCalendarMorningFace.setImageResource(returnToothResSmall(data.morningCount))
                            tvCalendarMorningTime.text = data.morningTime
                        } else if (data.morningTime.equals("0")) {
                            ivCalendarMorningStatus.setImageResource(R.drawable.group_254)
                            ivCalendarMorningFace.setImageResource(R.drawable.basic_off_close)
                            tvCalendarMorningTime.setText("-- : --")
                        }
                        if (!data.afternoonTime.equals("0")) {
                            ivCalendarAfternoonStatus.setImageResource(returnToothResBig(data.afternoonCount))
                            ivCalendarAfternoonFace.setImageResource(returnToothResSmall(data.afternoonCount))
                            tvCalendarAfternoonTime.text = data.afternoonTime
                        } else if (data.afternoonTime.equals("0")) {
                            ivCalendarAfternoonStatus.setImageResource(R.drawable.group_254)
                            ivCalendarAfternoonFace.setImageResource(R.drawable.basic_off_close)
                            tvCalendarAfternoonTime.text = "-- : --"
                        }
                        if (!data.dinnerTime.equals("0")) {
                            ivCalendarEveningStatus.setImageResource(returnToothResBig(data.dinnerCount))
                            ivCalendarEveningFace.setImageResource(returnToothResSmall(data.dinnerCount))
                            tvCalendarEveningTime.text = data.dinnerTime
                        } else if (data.dinnerTime.equals("0")) {
                            ivCalendarEveningStatus.setImageResource(R.drawable.group_254)
                            ivCalendarEveningFace.setImageResource(R.drawable.basic_off_close)
                            tvCalendarEveningTime.text = "-- : --"
                        }
                        if (!data.nightTime.equals("0")) {
                            ivCalendarNightStatus.setImageResource(returnToothResBig(data.nightCount))
                            ivCalendarNightFace.setImageResource(returnToothResSmall(data.nightCount))
                            tvCalendarNightTime.text = data.nightTime
                        } else if (data.nightTime.equals("0")) {
                            ivCalendarNightStatus.setImageResource(R.drawable.group_254)
                            ivCalendarNightFace.setImageResource(R.drawable.basic_off_close)
                            tvCalendarNightTime.text = "-- : --"
                        }
                    }
                }

                override fun onFailure(call: Call<ToothInfoDTO?>, t: Throwable) {}
            })
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

    companion object {
        const val INIT_STATE = 0
        const val url = "http://113.198.235.232:3000/"
    }

    inner class TodayDecorator: DayViewDecorator {
        private val today = CalendarDay.today()

        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return today == day
        }

        override fun decorate(view: DayViewFacade?) {

        }

    }
}