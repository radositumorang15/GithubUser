package com.dicoding.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.githubuser.entity.FavoriteUser

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract class FavoriteUserDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteUserDao

    companion object {
        @Volatile
        var INSTANCE: FavoriteUserDatabase? = null

        fun getInstance(context: Context): FavoriteUserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteUserDatabase::class.java,
                    "favorite_user_database" // Consistent name with the error message
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}