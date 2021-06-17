package deu.cse.tos.activity.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import deu.cse.tos.R
import deu.cse.tos.adapter.QnaHashRecyclerAdapter
import deu.cse.tos.adapter.QnaRecyclerAdapter
import deu.cse.tos.data.QnaDTO
import deu.cse.tos.data.QnaData
import deu.cse.tos.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.fragment_qna.*
import kotlinx.android.synthetic.main.fragment_qna.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class QnaFragment : Fragment() {
    private lateinit var qnaData: ArrayList<QnaData>
    private lateinit var hashData: ArrayList<String>
    private lateinit var hashTag: ArrayList<String>
    private lateinit var qnaAdapter: QnaRecyclerAdapter
    private lateinit var hashAdapter: QnaHashRecyclerAdapter
    private var input: HashMap<String, Any> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_qna, container, false)

        rootView.qna_refresh.setOnRefreshListener {
            qna_refresh.isRefreshing = false
        }

        RetrofitBuilder.api.postQnaResult(input).enqueue(object : Callback<QnaDTO> {
            override fun onResponse(call: Call<QnaDTO>, response: Response<QnaDTO>) {
                if (response.isSuccessful) {
                    val data = response.body()!!
                    val hashTmp = HashSet<String>()
                    val qnaTmp: HashSet<QnaData> = HashSet<QnaData>()

                    data.data?.forEach{
                        qnaTmp.add(QnaData(it.question, it.answer, it.tag))
                        hashTmp.add(it.tag)
                        log(hashTmp.toString())
                    }

                    qnaData = ArrayList(qnaTmp)
                    hashData = ArrayList(hashTmp)

                    qnaAdapter = QnaRecyclerAdapter(rootView.context, qnaData)
                    qna_board.adapter = qnaAdapter
                    val qnaLayout = LinearLayoutManager(rootView.context)
                    qna_board.layoutManager = qnaLayout
                    qna_board.setHasFixedSize(true)

                    hashAdapter = QnaHashRecyclerAdapter(rootView.context, hashData)
                    qna_hash_tag.adapter = hashAdapter
                    val hashLayout = LinearLayoutManager(rootView.context)
                    qna_hash_tag.layoutManager = hashLayout
                    qna_hash_tag.setHasFixedSize(true)
                }
            }

            override fun onFailure(call: Call<QnaDTO>, t: Throwable) {
                log("통신 실패 : $t")
            }

        })

        return rootView
    }



    private fun log(msg: String){
        Log.e("QnaFragment", msg)
    }
}