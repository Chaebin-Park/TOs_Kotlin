package deu.cse.tos.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import deu.cse.tos.R
import deu.cse.tos.activity.DetailQnaActivity
import deu.cse.tos.data.QnaData

class QnaRecyclerAdapter(private val context: Context, private val items: ArrayList<QnaData>) :
    RecyclerView.Adapter<QnaRecyclerAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val qnaQuestion = itemView.findViewById<TextView>(R.id.tv_question)
        private val qnaAnswer = itemView.findViewById<TextView>(R.id.tv_answer)

        fun bind(qnaData: QnaData, context: Context){
            qnaQuestion.text = qnaData.question
            qnaAnswer.text = qnaData.answer

            itemView.setOnClickListener{
                Intent(context, DetailQnaActivity::class.java).apply {
                    putExtra("DATA", qnaData)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.qna_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], context)
    }

    override fun getItemCount(): Int = items.size
}