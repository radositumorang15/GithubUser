package com.dicoding.githubuser.ui

import UserAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.response.GithubResponse
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.model.DarkViewModel
import com.dicoding.githubuser.setting.SettingPreferences
import com.dicoding.githubuser.setting.ViewModelFactoryDark
import com.dicoding.githubuser.setting.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter

    companion object {
        private const val TAG = "MainActivity"
        private const val Q = "rado"

    }

    private lateinit var darkButton: ImageButton
    private lateinit var favButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            ViewModelFactoryDark(pref)
        )[DarkViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        userAdapter = UserAdapter()
        binding.rvUser.adapter = userAdapter
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.rvUser.addItemDecoration(itemDecoration)
        darkButton = findViewById<ImageButton>(R.id.darkButton)
        favButton = findViewById<ImageButton>(R.id.favButton)
        getUsers()

        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchView.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val newValue = binding.searchView.text.toString()
                binding.searchBar.setText(newValue)
                searchUsers(newValue)
                binding.searchView.hide()
                Toast.makeText(this@MainActivity, newValue, Toast.LENGTH_SHORT).show()
                true
            } else {
                false
            }
        }

        darkButton.setOnClickListener {
            val intent = Intent(this, DarkActivity::class.java)
            startActivity(intent)
        }

        favButton.setOnClickListener {
            val intent = Intent(this, FavActivity::class.java)
            startActivity(intent)
        }


    }

    private fun searchUsers(q: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().searchUsers(q)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUserData(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getUsers() {
        showLoading(true)
        val client = ApiConfig.getApiService().searchUsers(Q)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val itemsList = responseBody.items
                        setUserData(itemsList)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    private fun setUserData(usersList: List<ItemsItem>?) {
        if (usersList != null) {
            userAdapter.submitList(usersList)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}

