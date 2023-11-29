package com.example.mystoryapp.ui.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mystoryapp.R
import com.example.mystoryapp.api.response.ListStoryItem
import com.example.mystoryapp.databinding.ActivityDetailStoryBinding
import com.example.mystoryapp.remote.withDateFormat

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_UPLOAD_DATE = "extra_upload_date"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_NAME)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val tvStory = intent.getStringExtra(EXTRA_IMAGE)
        val uploadDate = intent.getStringExtra(EXTRA_UPLOAD_DATE)

        binding.tvUsername.text = username
        binding.tvDescription.text = description
        binding.tvUploadDate.text = uploadDate
        Glide.with(binding.ivStory)
            .load(tvStory)
            .into(binding.ivStory)

//        val story = if (Build.VERSION.SDK_INT >= 33) {
//            intent.getParcelableExtra(EXTRA_STORY, ListStoryItem::class.java)
//        } else {
//            @Suppress("DEPRECATION")
//            intent.getParcelableExtra(EXTRA_STORY)
//        }
//
//        if (story != null) {
//            binding.tvUsername.text = story.name
//            binding.tvDescription.text = story.description
//            binding.tvUploadDate.text = story.createdAt.withDateFormat()
//            Glide.with(binding.ivStory)
//                .load(story.photoUrl)
//                .into(binding.ivStory)
//        }
        setAction()
    }

    private fun setAction() {
        binding.close.setOnClickListener {
            finish()
        }
    }
}