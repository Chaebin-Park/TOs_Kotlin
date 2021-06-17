package deu.cse.tos.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import deu.cse.tos.R
import deu.cse.tos.adapter.OralSuppliesAdapter
import deu.cse.tos.data.BrushListDTO
import deu.cse.tos.data.LoginData
import deu.cse.tos.data.OralSupplies
import deu.cse.tos.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_brush_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class BrushListActivity : AppCompatActivity() {
    private lateinit var mIntent: Intent
    private lateinit var loginData: LoginData
    private lateinit var items: List<BrushListDTO.BrushDTO>
    private lateinit var oralSuppliesAdapter: OralSuppliesAdapter
    private var oralItems: ArrayList<OralSupplies> = ArrayList()
    private var input: HashMap<String, Any> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brush_list)

        loginData = intent.getSerializableExtra("LOGIN") as LoginData

        oralSuppliesAdapter = OralSuppliesAdapter(this, oralItems, onClickItem)
        createRecyclerView()
        oralSuppliesAdapter.notifyDataSetChanged()
        mIntent = Intent(this, AddBrushListActivity::class.java)

        getAPI()

        floating_action_button.setOnClickListener{
            mIntent.putExtra("LOGIN", loginData)
            mIntent.putExtra("ITEM_NAME", "--")
            mIntent.putExtra("BUY_DATE", " ")
            mIntent.putExtra("USING_DATE", "--")
            startActivity(mIntent)
        }
    }

    private val onClickItem =
        View.OnClickListener { v ->
            val item = oralSuppliesAdapter.getItem(v.tag as Int)
            mIntent.putExtra("ITEM_NAME", item.itemName)
            mIntent.putExtra("BUY_DATE", items[v.tag as Int].buyDate)
            mIntent.putExtra("USING_DATE", items[v.tag as Int].usingDate)
            startActivity(mIntent)
        }

    private fun createRecyclerView(){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_oral_supplies.layoutManager = layoutManager
        rv_oral_supplies.adapter = oralSuppliesAdapter
        rv_oral_supplies.setHasFixedSize(true)
    }

    private fun getAPI(){
        input.put("hash_key", R.string.kakao_native_app_key)
        RetrofitBuilder.api.postBrushListSelectResult(input).enqueue(object : Callback<BrushListDTO>{
            override fun onResponse(call: Call<BrushListDTO>, response: Response<BrushListDTO>) {
                if (response.isSuccessful){
                    val data = response.body()!!
                    if(data.data != null) {
                        items = data.data!!
                        
                        for (item in items) {
                            oralItems.add(
                                OralSupplies(
                                    item.remainReccDate,
                                    item.itemName,
                                    item.reccDate
                                )
                            )
                            oralSuppliesAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BrushListDTO>, t: Throwable) {
                log("$t")
            }
        })
    }

    private fun log(msg: String){
        Log.e("BrushListActivity", msg)
    }
}