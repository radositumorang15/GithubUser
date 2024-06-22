package com.dicoding.githubuser.entity


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Entity(tableName = "favoriteuser")
@Parcelize
data class FavoriteUser(

    @field:SerializedName("login")
    @ColumnInfo(name = "login")
    val login: String,

    @field:SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @field:SerializedName("id")
    @PrimaryKey
    val id: Int,

    ): Parcelable