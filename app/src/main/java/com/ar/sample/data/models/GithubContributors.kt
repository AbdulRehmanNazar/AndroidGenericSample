package com.ar.sample.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


/**
 * @Author: Abdul Rehman
 */
@Entity
data class GithubContributors(
    @SerializedName("login") var name: String,
    @SerializedName("avatar_url") var imageURL: String,
    @SerializedName("contributions") var contributions: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}