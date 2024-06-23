package com.example.githubuserrev1.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favourites")
class Favourite(

    @ColumnInfo(name = "username")
    var username: String?,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String?,

    @ColumnInfo(name = "followers")
    val followers: Int?,

    @ColumnInfo(name = "following")
    val following: Int?,

    @ColumnInfo(name = "followers_url")
    val followersUrl: String?,

    @ColumnInfo(name = "following_url")
    val followingUrl: String?,

    @ColumnInfo(name = "repository")
    val repository: Int?,

    @ColumnInfo(name = "location")
    val location: String?,

    @ColumnInfo(name = "company")
    val company: String?,

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long? = null
)