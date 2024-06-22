package com.dicoding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.response.FollowResponse
import com.dicoding.githubuser.databinding.FollowingLayoutBinding

class FollowingAdapter(private val listFollowing: List<FollowResponse>): RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    class ViewHolder(var binding: FollowingLayoutBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FollowingLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listFollowing.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listFollowing[position]
        Glide.with(holder.binding.root.context)
            .load(user.avatarUrl)
            .into(holder.binding.tvAvatar2)
        holder.binding.tvUserName2.text = user.login


    }
}