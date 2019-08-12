package com.neirx.githubsearch.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.neirx.githubsearch.R
import com.neirx.githubsearch.model.Repository
import com.neirx.githubsearch.ui.main.adapter.RepositoryAdapter.RepositoryViewHolder
import kotlinx.android.synthetic.main.adapter_repository.view.*
import java.util.*

/**
 * Created by Waide Shery on 12.08.19.
 */
class RepositoryAdapter : Adapter<RepositoryViewHolder>() {
    interface OnRepositoryClickListener {
        fun onRepositoryClick(repository: Repository)
    }

    private val repositories: MutableList<Repository> = ArrayList()
    var onRepositoryClickListener: OnRepositoryClickListener? = null

    fun setRepositories(repositories: List<Repository>) {
        this.repositories.clear()
        this.repositories.addAll(repositories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.adapter_repository, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bindRepository(repositories[position])
    }

    inner class RepositoryViewHolder(itemView: View) : ViewHolder(itemView) {
        lateinit var repository: Repository

        init {
            itemView.setOnClickListener {
                onRepositoryClickListener?.onRepositoryClick(repository as Repository)
            }
        }

        fun bindRepository(repository: Repository) {
            this.repository = repository
            itemView.tvName.text = repository.name
            itemView.tvDescription.text = repository.description
            itemView.tvStars.text = makeStarsCount(repository.stars)
        }

        private fun makeStarsCount(stars: Long): String {
            return when (stars) {
                in 1000..999_999 -> {
                    String.format("%.1fk", stars.toFloat() / 1000)
                }
                in 1000_000..999_999_999 -> {
                    String.format("%.1fm", stars.toFloat() / 1000_000)
                }
                else -> stars.toString()
            }
        }
    }
}
