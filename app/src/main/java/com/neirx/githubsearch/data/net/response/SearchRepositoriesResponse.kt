package com.neirx.githubsearch.data.net.response

import com.google.gson.annotations.SerializedName
import com.neirx.githubsearch.data.net.item.RepositoryItem

/**
 * Created by Waide Shery on 12.08.19.
 */
class SearchRepositoriesResponse {

    @SerializedName("items")
    lateinit var items: List<RepositoryItem>

    /*
    {
    "message": "Validation Failed",
    "errors": [
        {
            "resource": "Search",
            "field": "q",
            "code": "missing"
        }
    ],
    "documentation_url": "https://developer.github.com/v3/search"
}
     */
}