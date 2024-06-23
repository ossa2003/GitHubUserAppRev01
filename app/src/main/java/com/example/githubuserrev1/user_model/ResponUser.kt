package com.example.githubuserrev1.user_model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponUser(

    @field:SerializedName("items")
    val users: List<ListUser>,

    @field:SerializedName("total_count")
    val totalCount: Int

) : Parcelable