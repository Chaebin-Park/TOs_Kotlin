package deu.cse.tos.activity.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import deu.cse.tos.R
import deu.cse.tos.activity.BrushListActivity
import deu.cse.tos.data.InsightDTO
import deu.cse.tos.data.LoginData
import deu.cse.tos.data.YearGraphDTO
import deu.cse.tos.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.fragment_information.view.*
import org.eazegraph.lib.models.BarModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InformationFragment : Fragment() {
    private lateinit var loginData: LoginData
    private lateinit var bundle: Bundle
    private lateinit var intent: Intent

    private val input: HashMap<String, Any> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_information, container, false)
        bundle = requireArguments()
        loginData = bundle.getSerializable("LOGIN") as LoginData
        intent = Intent(activity, BrushListActivity::class.java)

        val bottomAnimation = AnimationUtils.loadAnimation(context, R.anim.bottom_animation)

        rootView.linearLayout2.animation = bottomAnimation
        rootView.info_name_score.animation = bottomAnimation
        rootView.info_score.animation = bottomAnimation
        rootView.info_percent.animation = bottomAnimation

        rootView.info_user_name.text = "${loginData.name} 님"
        rootView.info_name_score.text = "${loginData.name} 님의 구강점수"

        input.put("hash_key", R.string.kakao_native_app_key)
        RetrofitBuilder.api.postYearGraphResult(input).enqueue(object : Callback<YearGraphDTO> {
            override fun onResponse(call: Call<YearGraphDTO>, response: Response<YearGraphDTO>) {
                if (response.isSuccessful) {
                    val data = response.body()!!

                    rootView.barChart.addBar(BarModel("JAN", data.jan, -0x674043))
                    rootView.barChart.addBar(BarModel("FEB", data.feb, -0x674043))
                    rootView.barChart.addBar(BarModel("MAR", data.mar, -0x674043))
                    rootView.barChart.addBar(BarModel("APR", data.apr, -0x674043))
                    rootView.barChart.addBar(BarModel("MAY", data.may, -0x674043))
                    rootView.barChart.addBar(BarModel("JUN", data.jun, -0x674043))
                    rootView.barChart.addBar(BarModel("JUL", data.jul, -0x674043))
                    rootView.barChart.addBar(BarModel("AUG", data.aug, -0x674043))
                    rootView.barChart.addBar(BarModel("SEP", data.sep, -0x674043))
                    rootView.barChart.addBar(BarModel("OCT", data.oct, -0x674043))
                    rootView.barChart.addBar(BarModel("NOV", data.nov, -0x674043))
                    rootView.barChart.addBar(BarModel("DEC", data.dec, -0x674043))
                    rootView.barChart.bringToFront()
                    rootView.barChart.startAnimation()

                    log("${data.toString()}")
                }
            }

            override fun onFailure(call: Call<YearGraphDTO>, t: Throwable) {
                log("통신 실패 : $t")
            }
        })

        RetrofitBuilder.api.postInsightResult(input).enqueue(object : Callback<InsightDTO> {
            override fun onResponse(call: Call<InsightDTO>, response: Response<InsightDTO>) {
                if (response.isSuccessful) {
                    val data = response.body()!!
                    rootView.info_score.text = data.score.toString()
                    rootView.info_brush_time.text = "${data.number} 회"
                }
            }

            override fun onFailure(call: Call<InsightDTO>, t: Throwable) {
                log("통신 실패 : $t")
            }

        })

        val monthAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.nal,
            android.R.layout.simple_spinner_dropdown_item
        )
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rootView.spinner.adapter = monthAdapter

        rootView.spinner.bringToFront()

        rootView.btn_oral.setOnClickListener{
            intent.putExtra("LOGIN", loginData)
            startActivity(intent)
        }

        return rootView
    }


    private fun log(msg: String){
        Log.e("InformationFragment", msg)
    }

    companion object {

    }
}