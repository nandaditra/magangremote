package com.example.magangremote.ui.home

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magangremote.api.ApiConfig
import com.example.magangremote.databinding.ActivityHomeBinding
import com.example.magangremote.model.JobResponse
import com.example.magangremote.model.JobsResultsItem
import com.example.magangremote.model.Lowongan
import com.example.magangremote.ui.detail.DetailActivity
import com.example.magangremote.ui.notification.NotificationActivity
import com.example.magangremote.ui.profile.ProfileActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var fireStoreDatabase:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fireStoreDatabase = FirebaseFirestore.getInstance()

        val layoutManager = LinearLayoutManager(this)
        binding.rvJobs.layoutManager = layoutManager
        binding.rvJobs.setHasFixedSize(true)
        supportActionBar?.setIcon(com.example.magangremote.R.drawable.icon_action_bar)

        supportActionBar?.setDisplayUseLogoEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        getListLowongan();
    }

    private fun getListLowongan() {
        binding.progressBar.visibility = View.VISIBLE
        val client = ApiConfig.getApiService().getAllListJobs(query, location, ltype)
        client.enqueue(object: Callback<JobResponse> {
            override fun onResponse(call: Call<JobResponse>, response: Response<JobResponse>) {
                binding.progressBar.visibility = View.INVISIBLE
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setListJob(responseBody.jobsResults)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<JobResponse>, t: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getListLowonganByKeyword(){

    }

    private fun setListJob(listJob: List<JobsResultsItem>) {
        val listLowongan = ArrayList<Lowongan>()

        for (job in listJob) {
            val id= UUID.randomUUID().toString();
            val jobTitle = job.title
            val company = job.companyName
            val location = job.location
            val postTime = job.extensions[0]

            val inputLowongan = Lowongan(id, jobTitle,company,location, postTime)
            listLowongan.add(inputLowongan)

            val documentReference = fireStoreDatabase.collection("lowongan").document(id)
            documentReference.set(inputLowongan).addOnSuccessListener(OnSuccessListener {
                Log.d(TAG, "lowongan successfully written!")
            }).addOnFailureListener { e ->
                // Handle any errors
                Log.w(TAG, "Error writing lowongan", e)
            }

        }

        Log.d("Result Data", "$listLowongan")

        val adapter = LowonganAdapter(listLowongan)
        binding.rvJobs.adapter = adapter

        adapter.setOnItemClickCallback(object : LowonganAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Lowongan) {
                Toast.makeText(this@HomeActivity, "You choose "+data.company, Toast.LENGTH_SHORT).show()
                var moveDetail = Intent(this@HomeActivity, DetailActivity::class.java)

                moveDetail.putExtra(DetailActivity.EXTRA_NAME, data.jobName)
                moveDetail.putExtra(DetailActivity.EXTRA_COMPANY, data.company)
                moveDetail.putExtra(DetailActivity.EXTRA_LOCATION, data.location)
                startActivity(moveDetail)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.example.magangremote.R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            com.example.magangremote.R.id.notification_menu-> {
                val i = Intent(this, NotificationActivity::class.java)
                startActivity(i)
                true
            }
            com.example.magangremote.R.id.profile_menu -> {
                val i = Intent(this, ProfileActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }

    companion object {
        var query: String = "intern"
        var location: String = "indonesia"
        var ltype: Int = 1
        const val TAG = "HomeActivity"
    }
}