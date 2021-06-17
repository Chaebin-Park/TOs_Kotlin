package deu.cse.tos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import deu.cse.tos.R
import deu.cse.tos.data.QnaData
import kotlinx.android.synthetic.main.activity_detail_qna.*

class DetailQnaActivity : AppCompatActivity() {
    private lateinit var qnaData: QnaData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_qna)
        qnaData = intent.getSerializableExtra("DATA") as QnaData

        tv_question_title.text = qnaData.question
        tv_qna_hash_tag.text = qnaData.tag
        tv_question_answer.text = qnaData.answer

    }
}