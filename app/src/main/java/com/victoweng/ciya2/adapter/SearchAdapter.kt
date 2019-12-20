package com.victoweng.ciya2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.victoweng.ciya2.R
import com.victoweng.ciya2.data.EventDetail
import kotlinx.android.synthetic.main.search_event_item.view.*

class SearchAdapter(val clickListener: (EventDetail) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var eventList: List<EventDetail> = ArrayList()

    fun setEventList(eventList: List<EventDetail>) {
        this.eventList = eventList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_event_item, parent, false)
        return EventDetailViewHolder(view)
    }

    override fun getItemCount() = eventList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is EventDetailViewHolder -> holder.onBind(eventList[position], clickListener)
        }
    }

    class EventDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val eventName = itemView.event_name

        fun onBind(eventDetail: EventDetail, clickListener: (EventDetail) -> Unit) {

            eventName.text = eventDetail.title
            itemView.setOnClickListener { clickListener(eventDetail) } 
        }
    }

}