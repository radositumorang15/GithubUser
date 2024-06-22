package com.dicoding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.response.FollowResponse
import com.dicoding.githubuser.databinding.FollowersLayoutBinding

 class FollowersAdapter(private val listFollowers: List<FollowResponse>): RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {
        class ViewHolder(var binding: FollowersLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = FollowersLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int = listFollowers.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val user = listFollowers[position]
            Glide.with(holder.binding.root.context)
                .load(user.avatarUrl)
                .into(holder.binding.tvAvatar1)
            holder.binding.tvUserName1.text = user.login

        }
}