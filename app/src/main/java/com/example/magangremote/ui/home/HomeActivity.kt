package com.example.magangremote.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magangremote.api.ApiConfig
import com.example.magangremote.auth.UserPreferences
import com.example.magangremote.databinding.ActivityHomeBinding
import com.example.magangremote.model.JobResponse
import com.example.magangremote.model.JobsResultsItem
import com.example.magangremote.model.Lowongan
import com.example.magangremote.ui.detail.DetailActivity
import com.example.magangremote.ui.notification.NotificationActivity
import com.example.magangremote.ui.profile.ProfileActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity() {
    private lateinit var dataPreferences: UserPreferences
    private lateinit var binding: ActivityHomeBinding
    private lateinit var fireStoreDatabase:FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fireStoreDatabase = FirebaseFirestore.getInstance()
        firebaseAuth = Firebase.auth
        dataPreferences = UserPreferences(this)

        val model = ViewModelProvider(this)[HomeViewModel::class.java]
        val layoutManager = LinearLayoutManager(this)

        binding.rvJobs.layoutManager = layoutManager
        binding.rvJobs.setHasFixedSize(true)

        supportActionBar?.setIcon(com.example.magangremote.R.drawable.icon_action_bar)
        supportActionBar?.setDisplayUseLogoEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        var newQuery:String = ""
        var newLocation:String = ""

        if(newQuery.isEmpty() && newLocation.isEmpty())  {
            model.getListLowongan(query, location, ltype)
            model.listLowongan.observe(this){ responseBody ->
                showListLowongan(responseBody)
            }
        }

        binding.imgEmpty.visibility = View.GONE
        binding.btnSearch.setOnClickListener {
            val query = binding.textInput.text.toString().trim();
            val location = binding.locationInput.text.toString().trim();
            newQuery = query
            newLocation = location
            model.getListLowonganByKeyword(query, location)
            model.listLowongan.observe(this){ responseBody ->
                showListLowongan(responseBody)
            }
        }

        model.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showListLowongan(listJob: List<JobsResultsItem>) {
        val listLowongan = ArrayList<Lowongan>()

        if(listJob == null) {
            binding.rvJobs.visibility = View.GONE
            binding.imgEmpty.visibility = View.VISIBLE
        } else {
            binding.rvJobs.visibility = View.VISIBLE
            binding.imgEmpty.visibility = View.GONE
            for (job in listJob) {
                val id= job.jobId
                val jobTitle = job.title
                val company = job.companyName
                val location = job.location
                val image = job.thumbnail
                val description = job.description
                val urlJob = ""
                val postTime = job.extensions[0]

                val inputLowongan = Lowongan(id, jobTitle,company,location, image, description,urlJob, postTime)
                listLowongan.add(inputLowongan)
            }
        }


        val adapter = LowonganAdapter(listLowongan)
        binding.rvJobs.adapter = adapter

        adapter.setOnItemClickCallback(object : LowonganAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Lowongan) {
                Toast.makeText(this@HomeActivity, "You choose "+data.company, Toast.LENGTH_SHORT).show()
                var moveDetail = Intent(this@HomeActivity, DetailActivity::class.java)

                moveDetail.putExtra(DetailActivity.EXTRA_ID, data.id)
                moveDetail.putExtra(DetailActivity.EXTRA_NAME, data.jobName)
                moveDetail.putExtra(DetailActivity.EXTRA_DESC, data.description)
                moveDetail.putExtra(DetailActivity.EXTRA_IMG,data.imageCompany )
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        var query: String = "intern"
        var location: String = "indonesia"
        var ltype: Int = 1
        const val TAG = "HomeActivity"
    }
}