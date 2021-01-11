package ru.trinitydigital.radio.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.trinitydigital.radio.R
import ru.trinitydigital.radio.data.RadioStations

class RadioAdapter(
    private val listener: (item: RadioStations) -> Unit
) : RecyclerView.Adapter<RadioVH>() {

    private var list = arrayListOf<RadioStations>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_radiostations, parent, false)

        return RadioVH(view)
    }

    override fun onBindViewHolder(holder: RadioVH, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            listener.invoke(list[position])
        }
    }

    fun update(data: List<RadioStations>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()

    }

    override fun getItemCount() = list.size
}


class RadioVH(view: View) : RecyclerView.ViewHolder(view) {

    private var tvTitle: TextView = itemView.findViewById(R.id.tvRadio)

    fun bind(radioStations: RadioStations) {
        tvTitle.text = radioStations.name
    }

}