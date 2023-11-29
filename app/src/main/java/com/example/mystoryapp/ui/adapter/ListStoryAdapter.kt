package com.example.mystoryapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystoryapp.api.response.ListStoryItem
import com.example.mystoryapp.databinding.StoryItemsBinding
import com.example.mystoryapp.remote.withDateFormat
import com.example.mystoryapp.ui.activity.DetailStoryActivity

class ListStoryAdapter: PagingDataAdapter<ListStoryItem, ListStoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    class MyViewHolder(private val binding: StoryItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            binding.tvUsername.text = data.name
            binding.tvUploadDate.text = data.createdAt.withDateFormat()
            Glide.with(binding.tvImageStory)
                .load(data.photoUrl)
                .into(binding.tvImageStory)
            binding.storyCard.setOnClickListener {
                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.EXTRA_NAME, data.name)
                intent.putExtra(DetailStoryActivity.EXTRA_DESCRIPTION, data.description)
                intent.putExtra(DetailStoryActivity.EXTRA_UPLOAD_DATE, data.createdAt)
                intent.putExtra(DetailStoryActivity.EXTRA_IMAGE, data.photoUrl)
                itemView.context.startActivities(arrayOf(intent))
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = StoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
}