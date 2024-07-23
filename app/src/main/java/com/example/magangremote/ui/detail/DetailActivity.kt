package com.example.magangremote.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.magangremote.api.ApiConfig
import com.example.magangremote.api.ApplyOptionsItem
import com.example.magangremote.api.LowonganDetailResponse
import com.example.magangremote.databinding.ActivityDetailBinding
import com.example.magangremote.repository.LowonganRepository
import com.example.magangremote.ui.home.HomeActivity
import com.example.magangremote.ui.home.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailBinding
    companion object {
        const val TAG = "DetailActivity"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_LOCATION = "extra_location"
        const val EXTRA_COMPANY = "extra_company"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_IMG = "extra_img"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra(EXTRA_NAME)
        val location = intent.getStringExtra(EXTRA_LOCATION)
        val id = intent.getStringExtra(EXTRA_ID)
        val imageUrl = intent.getStringExtra(EXTRA_IMG)
        val desc = intent.getStringExtra(EXTRA_DESC)
        val company = intent.getStringExtra(EXTRA_COMPANY)

        val model = ViewModelProvider(this)[DetailViewModel::class.java]

        showLowonganDetail(name, location, imageUrl, desc, company)

        if (id != null) {
            model.getLowonganDetail(id)
            model.lowongan.observe(this){data->
                moveLamar(data)
            }
        }
    }

    private fun showLowonganDetail(name:String?, location:String?, imageUrl:String?, desc:String?, company:String?){
        binding.apply {
            jobTitle.text = name
            companyName.text = company
            if (imageUrl == null) {
                Glide.with(this@DetailActivity)
                    .load("https://d1csarkz8obe9u.cloudfront.net/posterpreviews/a-logo-design%7C-alphabet-a-logo-template-050582c8d49a32bf27aa520434205024_screen.jpg?ts=1663131054")
                    .apply(RequestOptions().override(1000, 1000))
                    .into(companyImg)
            } else {
                Glide.with(this@DetailActivity)
                    .load(imageUrl)
                    .apply(RequestOptions().override(1000, 1000))
                    .into(companyImg)
            }
            locationJob.text = location
            detailWork.text = desc
        }
    }

    private fun moveLamar(data:List<ApplyOptionsItem>?){
        binding.btnLamar.setOnClickListener {
            if(data != null) {
                val url = data[0].link
                val i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse(url))
                startActivity(i)
            }
        }
    }
}