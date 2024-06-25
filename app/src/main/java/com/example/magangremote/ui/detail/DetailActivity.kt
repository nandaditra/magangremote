package com.example.magangremote.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.magangremote.R
import com.example.magangremote.databinding.ActivityDetailBinding
import com.example.magangremote.databinding.ActivityHomeBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_LOCATION = "extra_location"
        const val EXTRA_COMPANY = "extra_company"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_LINK = "extra_link"
        const val EXTRA_IMG = "extra_img"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra(EXTRA_NAME)
        val location = intent.getStringExtra(EXTRA_LOCATION)
        val desc = intent.getStringExtra(EXTRA_DESC)
        val company = intent.getStringExtra(EXTRA_COMPANY)

        binding.apply {
            jobTitle.text = name
            companyName.text = company
            locationJob.text = location
        }
    }
}