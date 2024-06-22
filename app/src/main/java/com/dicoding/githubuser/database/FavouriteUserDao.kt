package com.dicoding.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.githubuser.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(user: FavoriteUser)

    @Query("SELECT * FROM favoriteuser")
    fun loadAllFavorite(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favoriteuser WHERE id = :id")
    fun findById(id: Int): LiveData<FavoriteUser> // Corrected the query to return FavoriteUser

    @Query("DELETE FROM favoriteuser WHERE id = :id")
    suspend fun deleteFavorite(id: Int)
}