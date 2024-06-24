package com.example.magangremote.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.magangremote.R
import com.example.magangremote.model.JobsResultsItem
import com.example.magangremote.model.Lowongan

class LowonganAdapter(private val listJob: List<Lowongan>): RecyclerView.Adapter<LowonganAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_job, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (id ,job, company, location, timestamp) = listJob[position]
        holder.tvJobTitle.text = job
        holder.tvCompany.text = company
        holder.tvLocation.text = location
        holder.tvTimeStamp.text = timestamp

    }

    override fun getItemCount() = listJob.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvJobTitle: TextView = view.findViewById(R.id.tv_job_name)
        val tvCompany: TextView = view.findViewById(R.id.tv_job_company)
        val tvLocation: TextView = view.findViewById(R.id.tv_job_location)
        val tvTimeStamp: TextView = view.findViewById(R.id.tv_job_timestamp)
    }

}