package com.neirx.githubsearch

import com.neirx.githubsearch.model.Repository
import kotlin.random.Random

/**
 * Created by Waide Shery on 12.08.19.
 */
object RepositoryUtil {
    fun randomRepositories(count: Int): List<Repository> {
        val list = ArrayList<Repository>(count)
        for (i in 1..count) {
            list.add(randomRepository())
        }
        return list
    }

    fun randomRepository(): Repository {
        val id = Random(10_000).nextLong()
        val stars = Random(10_000).nextLong()
        return Repository(
            id, "Name $id", "Description $id",
            stars, "https://github.com"
        )
    }
}
