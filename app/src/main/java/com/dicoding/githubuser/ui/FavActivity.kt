package com.dicoding.githubuser.ui

import UserAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.model.FavViewModel
import com.dicoding.githubuser.model.FavViewModelFactory

class FavActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fav)

        val factory: FavViewModelFactory = FavViewModelFactory.getInstance(application)
        val viewModel by viewModels<FavViewModel> { factory }

        adapter = UserAdapter()
        val recyclerView: RecyclerView = findViewById(R.id.rv_fav)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.getUserFav().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.login, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            adapter.submitList(items)
        }
    }
}