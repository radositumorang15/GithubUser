package com

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubuser.FollowersFragment
import com.dicoding.githubuser.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val login: Bundle) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when(position){
        0 -> FollowersFragment()
        1 -> FollowingFragment()
        else -> throw IllegalStateException("invalid fragment position")
    }.apply {
        arguments = login
    }
}