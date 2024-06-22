package com.dicoding.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.FollowersAdapter
import com.dicoding.githubuser.data.response.FollowResponse
import com.dicoding.githubuser.databinding.FragmentFollowersBinding
import com.dicoding.githubuser.model.FollowersViewModel



class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private val followersViewModel by viewModels<FollowersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username") ?: ""
        followersViewModel.users.observe(viewLifecycleOwner) {
            setFollowers(it)
        }
        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        followersViewModel.followers(username)
    }

    private fun setFollowers(listFollowers: List<FollowResponse>) {
        val adapter = FollowersAdapter(listFollowers)
        binding.rvFollowers.adapter = adapter
        binding.rvFollowers.layoutManager = LinearLayoutManager(requireContext())
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }

}