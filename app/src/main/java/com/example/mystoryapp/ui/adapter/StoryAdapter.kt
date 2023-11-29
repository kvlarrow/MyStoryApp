package com.example.mystoryapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystoryapp.api.response.ListStoryItem
import com.example.mystoryapp.R

class StoryAdapter(private val listStory: ArrayList<ListStoryItem>?) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivStory: ImageView = itemView.findViewById(R.id.tv_image_story)
        val tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        val tvUploadDate: TextView = itemView.findViewById(R.id.tv_upload_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StoryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.story_items, parent, false)
    )

    override fun getItemCount(): Int = listStory!!.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.tvUsername.text = listStory!![position].name
        holder.tvUploadDate.text = listStory[position].createdAt
        Glide.with(holder.ivStory)
            .load(listStory[position].photoUrl)
            .into(holder.ivStory)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listStory[holder.adapterPosition])
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem)
    }

}