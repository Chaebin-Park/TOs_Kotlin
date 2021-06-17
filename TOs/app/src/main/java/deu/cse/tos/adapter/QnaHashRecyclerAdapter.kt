package deu.cse.tos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import deu.cse.tos.R

class QnaHashRecyclerAdapter(private val context: Context, private val items: ArrayList<String>):
    RecyclerView.Adapter<QnaHashRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val hash: TextView = itemView.findViewById(R.id.tv_hash)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QnaHashRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.qna_hash_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: QnaHashRecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.hash.text = item
        holder.hash.tag = item

    }

    override fun getItemCount(): Int = items.size
}