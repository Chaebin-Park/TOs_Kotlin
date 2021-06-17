package deu.cse.tos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import deu.cse.tos.R
import deu.cse.tos.data.OralSupplies


class OralSuppliesAdapter(private val context: Context, private val item: ArrayList<OralSupplies>, private val onClickItem: View.OnClickListener):
    RecyclerView.Adapter<OralSuppliesAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val list_remaining_date = itemView.findViewById<TextView>(R.id.tv_remaining_date)
        val list_item_name = itemView.findViewById<TextView>(R.id.tv_item_name)
        val list_rec_date = itemView.findViewById<TextView>(R.id.tv_recomended_date)
        val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.oral_supply_items, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val oralSupplies = item[position]
        holder.list_rec_date.text = oralSupplies.reccDate
        holder.list_item_name.text = oralSupplies.itemName
        holder.list_remaining_date.text = oralSupplies.remainDate.toString()
        holder.imageButton.tag = position
        holder.imageButton.setOnClickListener(onClickItem)
    }

    override fun getItemCount(): Int = item.size

    fun getItem(position: Int) : OralSupplies{
        return item[position]
    }
}