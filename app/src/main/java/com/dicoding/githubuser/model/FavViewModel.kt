package com.dicoding.githubuser.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.database.FavoriteUserDao
import com.dicoding.githubuser.database.FavoriteUserDatabase
import com.dicoding.githubuser.entity.FavoriteUser

class FavViewModel(application: Application): ViewModel() {
    private val mUserDao: FavoriteUserDao
    init {
        val favdb = FavoriteUserDatabase.getInstance(application)
        mUserDao = favdb.favoriteDao()
    }

    fun getUserFav() : LiveData<List<FavoriteUser>> = mUserDao.loadAllFavorite()
}