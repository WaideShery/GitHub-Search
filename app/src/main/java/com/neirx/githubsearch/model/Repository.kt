package com.neirx.githubsearch.model

/**
 * Created by Waide Shery on 11.08.19.
 */
data class Repository(
    val id: Long,
    val name: String,
    val description: String?,
    val stars: Long,
    val url: String
    )
