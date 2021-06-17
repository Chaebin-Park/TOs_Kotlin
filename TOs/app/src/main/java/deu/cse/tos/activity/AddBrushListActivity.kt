package deu.cse.tos.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import deu.cse.tos.R
import deu.cse.tos.activity.fragment.DatePickerFragment
import deu.cse.tos.data.CheckResultDTO
import deu.cse.tos.data.LoginData
import deu.cse.tos.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_add_brush_list_activty.*
import kotlinx.android.synthetic.main.item_bluetooth_device.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

@Suppress("DEPRECATION")
class AddBrushListActivity : AppCompatActivity() {
    private lateinit var loginData: LoginData
    private var input: HashMap<String, Any> = HashMap()
    private lateinit var buyDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_brush_list_activty)

        loginData = intent.getSerializableExtra("LOGIN") as LoginData
        tv_brush_list_name.text = loginData.name

        btn_set_date.setOnClickListener{
            showDatePicker(it)
        }

        radio_group.setOnCheckedChangeListener{ radioGroup: RadioGroup, i: Int ->
            radio_brush.setTextColor(resources.getColor(R.color.themecolor))
            radio_floss.setTextColor(resources.getColor(R.color.themecolor))
            radio_paste.setTextColor(resources.getColor(R.color.themecolor))
            radio_wash.setTextColor(resources.getColor(R.color.themecolor))

            when(i){
                R.id.radio_brush -> {
                    tv_brush_list_item.text = radio_brush.tag.toString()
                    radio_brush.setTextColor(resources.getColor(R.color.white))
                }
                R.id.radio_floss -> {
                    tv_brush_list_item.text = radio_floss.tag.toString()
                    radio_floss.setTextColor(resources.getColor(R.color.white))
                }
                R.id.radio_paste -> {
                    tv_brush_list_item.text = radio_paste.tag.toString()
                    radio_paste.setTextColor(resources.getColor(R.color.white))
                }
                R.id.radio_wash -> {
                    tv_brush_list_item.text = radio_wash.tag.toString()
                    radio_wash.setTextColor(resources.getColor(R.color.white))
                }
            }
        }

        btn_register.setOnClickListener{
            input.put("hash_key", R.string.kakao_native_app_key)
            input.put("item_name", tv_brush_list_item.text)
            input.put("buy_date", buyDate)
            RetrofitBuilder.api.postBrushListResult(input).enqueue(object :
                Callback<CheckResultDTO> {
                override fun onResponse(
                    call: Call<CheckResultDTO>,
                    response: Response<CheckResultDTO>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()!!
                        if (data.result == "true") {
                            val mIntent = Intent(
                                this@AddBrushListActivity,
                                BrushListActivity::class.java
                            )
                            startActivity(mIntent)
                        } else if (data.result == "flase") {
                            toast("올바르지 않은 입력입니다.")
                            log("올바르지 않은 입력입니다.")
                        }
                    }
                }

                override fun onFailure(call: Call<CheckResultDTO>, t: Throwable) {
                    log("통신실패 : $t")
                }

            })
        }
    }

    fun showDatePicker(v: View){
        val dpFragment = DatePickerFragment()
        dpFragment.show(supportFragmentManager, "datePicker")
    }

    fun processDatePickerResult(year: Int, month: Int, day: Int){
        val dateMessage = "$year 년 ${month+1} 월 $day 일"
        val sampleDate = "$year-${month+1}-$day 00:00:00"
        val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = sf.parse(sampleDate)
        buyDate = date.toString()
        val today = Calendar.getInstance()
        val diff = (today.time.time - date.time) / (60 * 60 * 24 * 1000)

        tv_brush_list_date.text = dateMessage
        tv_brush_list_used.text = diff.toString()
    }

    private fun toast(msg: String){
        Toast.makeText(this@AddBrushListActivity, msg, Toast.LENGTH_SHORT).show()
    }

    private fun log(msg: String){
        Log.e("AddBrushListActivity", msg)
    }
}