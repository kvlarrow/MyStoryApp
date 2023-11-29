package com.example.mystoryapp

import com.example.mystoryapp.api.response.ListStoryItem

object DataDummy {
    fun generateDummyData(): List<ListStoryItem> {
        val listStory: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..5) {
            val stories = ListStoryItem(
                "https://images.unsplash.com/photo-1687511901612-a9f441c596fe?auto=format&fit=crop&w=387&q=80",
                "2023-06-22T22:22:22Z",
                "Osas $i",
                "lorem ipsum ansu kafa $i kuts",
                "17.2460",
                "$i",
                "25.2435"
            )
            listStory.add(stories)
        }
        return listStory
    }
}