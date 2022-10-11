package com.example.sicreditest.feature.eventList.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sicreditest.R
import com.example.sicreditest.feature.eventList.model.DetailEvent
import com.squareup.picasso.Picasso

class ItemEventAdapter(private val events: List<DetailEvent>, private val clickListener: (DetailEvent) -> Unit) : RecyclerView.Adapter<ItemEventAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(
            item, clickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int {
        return events.size
    }

    class ViewHolder(view: View, private val onItemClicked: (id: DetailEvent) -> Unit) : RecyclerView.ViewHolder(view) {
        private val title: TextView
        private val image: ImageView
        init {
            title = view.findViewById(R.id.title_event_item)
            image = view.findViewById(R.id.image_event_item)
        }
        fun bind(event: DetailEvent) {
            title.text = event.title
            val urlImage = event.imageUrl
            Picasso.get()
                .load(urlImage)
                .placeholder(R.mipmap.sicredi)
                .into(image)
            itemView.setOnClickListener {onItemClicked(event)}
        }

    }
}