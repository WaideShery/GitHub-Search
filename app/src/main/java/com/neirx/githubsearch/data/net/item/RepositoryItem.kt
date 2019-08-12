package com.neirx.githubsearch.data.net.item

import com.google.gson.annotations.SerializedName

/**
 * Created by Waide Shery on 12.08.19.
 */
data class RepositoryItem(
    @SerializedName("id")
    val id: Long,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("stargazers_count")
    val stars: Long
)
