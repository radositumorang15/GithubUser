package com.dicoding.githubuser.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.SectionsPagerAdapter
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityDetailBinding
import com.dicoding.githubuser.entity.FavoriteUser
import com.dicoding.githubuser.model.DetailViewModel
import com.dicoding.githubuser.model.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {



    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        var favoriteUser = intent.getParcelableExtra<FavoriteUser>("item")
        var isFavorite = false


        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]


        showLoading(true)


        username?.let {
            viewModel.getDetailUser(it)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.detailUser.observe(this) { detailUser ->
            viewModel.setLoading(false)
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(detailUser.avatarUrl)
                    .into(tvAvatar)


                tvUserName.text = detailUser.login

                tvName.text = detailUser.name.toString()


                tvFollowers.text = "Following: ${detailUser.following}"


                TvFollowing.text = "Followers: ${detailUser.followers}"


                favoriteUser = FavoriteUser(
                    login = detailUser.login!!,
                    avatarUrl = detailUser.avatarUrl!!,
                    id = detailUser.id!!
                )

                favoriteUser?.let {
                    viewModel.findFavorite(it.id).observe(this@DetailActivity) { favorite ->
                        isFavorite = favorite != null
                        if (isFavorite) {
                            activeFavoriteButton()
                        } else {
                            inactiveFavoriteButton()
                        }
                    }
                }
            }
        }

        val login = Bundle().apply { putString("username", username) }
        val sectionsPagerAdapter = SectionsPagerAdapter(this, login)
        binding.viewPager.adapter = sectionsPagerAdapter


        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        viewModel.findFavorite(favoriteUser?.id ?: 0).observe(this) { favorite ->
            isFavorite = favorite != null
            if (isFavorite) {
                activeFavoriteButton()
            } else {
                inactiveFavoriteButton()
            }
        }

        // Set a click listener for the favorite button
        binding.fabLike.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                favoriteUser?.let {
                    viewModel.setFavorite(it)
                }
                activeFavoriteButton()
            } else {
                favoriteUser?.let {
                    viewModel.deleteFavorite(it.id)
                }
                inactiveFavoriteButton()
            }
        }
    }

    private fun activeFavoriteButton(){
        binding.fabLike.setImageResource(R.drawable.baseline_favorite_24)
    }

    private fun inactiveFavoriteButton(){
        binding.fabLike.setImageResource(R.drawable.baseline_favorite_border_24)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.content_tab_follower,
            R.string.content_tab_following
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}


