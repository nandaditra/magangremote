package com.example.magangremote.ui.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.magangremote.R
import com.example.magangremote.model.Notifikasi

class NotificationAdapter(private var listNotifikasi: List<Notifikasi>): RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val tvTitle: TextView = itemView.findViewById(R.id.tv_job_name)
       val tvMessage: TextView = itemView.findViewById(R.id.tv_job_company)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false))
    override fun getItemCount(): Int = listNotifikasi.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (title, message, _) = listNotifikasi[position]
        holder.tvTitle.text = title
        holder.tvMessage.text = message

        holder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked(listNotifikasi[holder.absoluteAdapterPosition])}
    }

    interface OnItemClickCallback {
        fun onItemClicked(job : Notifikasi)
    }

}