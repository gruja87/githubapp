package com.githubapp.ui.repolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.githubapp.R
import com.githubapp.data.models.Repo
import kotlinx.android.synthetic.main.item_repos_list.view.*

class RepoListAdapter
constructor(
    private var reposList: List<Repo> = ArrayList(),
    private val onRepoClickListener: OnRepoClickListener
) : RecyclerView.Adapter<RepoListAdapter.ReposListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposListItemViewHolder {
        return ReposListItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_repos_list,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ReposListItemViewHolder, position: Int) {
        holder.bind(reposList.get(position))
    }

    override fun getItemCount(): Int {
        return reposList.size
    }

    fun addReposList(reposList: List<Repo>) {
        this.reposList = reposList
        notifyDataSetChanged()
    }

    inner class ReposListItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(repo: Repo) {
            itemView.tv_repo_name.text = repo.full_name
            itemView.tv_repo_desc.text = repo.description
            itemView.tv_repo_open_issues.text = repo.open_issues_count.toString()

            itemView.setOnClickListener {
                onRepoClickListener.repoClicked(repo)
            }
        }
    }

    interface OnRepoClickListener {
        fun repoClicked(repo: Repo)
    }
}