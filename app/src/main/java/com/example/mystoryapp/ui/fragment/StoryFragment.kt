package com.example.mystoryapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystoryapp.api.response.ListStoryItem
import com.example.mystoryapp.databinding.FragmentStoryBinding
import com.example.mystoryapp.ui.activity.DetailStoryActivity
import com.example.mystoryapp.ui.activity.MainActivity
import com.example.mystoryapp.ui.adapter.ListStoryAdapter
import com.example.mystoryapp.ui.adapter.StoryAdapter
import com.example.mystoryapp.ui.viewModel.StoryViewModel

class StoryFragment : Fragment() {

    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!
    private val storyViewModel: StoryViewModel by viewModels{
        StoryViewModel.StoryViewModelFactory((activity as MainActivity).getToken())
    }
//    private val storyViewModel by viewModels<StoryViewModel>()
//    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)

//        storyViewModel.isLoading.observe(viewLifecycleOwner) {
//            showLoading(it)
//        }

        val layoutManager = LinearLayoutManager(context)
        binding.rvStories.layoutManager = layoutManager

        getStories()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ListStoryAdapter()
        binding.rvStories.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        binding.rvStories.layoutManager = layoutManager

        storyViewModel.listStory.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

    }

    private fun getStories() {
        val adapter = ListStoryAdapter()
        binding.rvStories.adapter = adapter
        storyViewModel.listStory.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        token = (activity as MainActivity).getToken()
//        storyViewModel.addStory(token)
//
//        storyViewModel.storyStatus.observe(viewLifecycleOwner) {
//            if (!it.error) {
//                storyViewModel.story.observe(viewLifecycleOwner) { story ->
//                    setStoryList(story)
//                }
//            }
//        }
//
//        storyViewModel.error.observe(viewLifecycleOwner) {
//            if (it.isNotEmpty()) {
//                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun setStoryList(story: List<ListStoryItem>?) {
//        val listStory = ArrayList<ListStoryItem>()
//        for (storyItem in story!!) {
//            listStory.add(storyItem)
//        }
//        val adapter = StoryAdapter(listStory)
//        binding.rvStories.adapter = adapter
//
//        adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: ListStoryItem) {
//                showSelectedStory(data)
//            }
//        })
//    }
//
//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }
//
//    private fun showSelectedStory(storyItem: ListStoryItem) {
//        val intent = Intent(activity, DetailStoryActivity::class.java)
//        intent.putExtra(DetailStoryActivity.EXTRA_STORY, storyItem)
//        startActivity(intent, activity?.let {
//            ActivityOptionsCompat.makeSceneTransitionAnimation(it).toBundle()
//        })
//    }

}