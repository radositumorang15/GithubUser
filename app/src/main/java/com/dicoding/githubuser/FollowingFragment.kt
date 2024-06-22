package com.dicoding.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.FollowingAdapter
import com.dicoding.githubuser.data.response.FollowResponse
import com.dicoding.githubuser.databinding.FragmentFollowingBinding
import com.dicoding.githubuser.model.FollowingViewModel

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private val followingViewModel by viewModels<FollowingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username") ?: ""
        followingViewModel.users.observe(viewLifecycleOwner) {
            setFollowing(it)
        }
        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        followingViewModel.following(username)
    }

    private fun setFollowing(listFollowing: List<FollowResponse>) {
        val adapter = FollowingAdapter(listFollowing)
        binding.rvFollowing.adapter = adapter
        binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}