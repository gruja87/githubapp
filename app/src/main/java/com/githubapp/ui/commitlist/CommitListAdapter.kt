package com.githubapp.ui.commitlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.githubapp.R
import com.githubapp.data.models.Commit
import kotlinx.android.synthetic.main.item_commit_list.view.*

class CommitListAdapter
constructor(
    private var commitList: List<Commit> = ArrayList()
) : RecyclerView.Adapter<CommitListAdapter.ReposListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposListItemViewHolder {
        return ReposListItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_commit_list,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ReposListItemViewHolder, position: Int) {
        holder.bind(commitList.get(position))
    }

    override fun getItemCount(): Int {
        return commitList.size
    }

    fun addCommitList(commitList: List<Commit>) {
        this.commitList = commitList
        notifyDataSetChanged()
    }

    inner class ReposListItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(commit: Commit) {
            itemView.tv_commit_commiter_name.text = commit.commit.committer.name
            itemView.tv_commit_message.text = commit.commit.message
            itemView.tv_commit_date.text = commit.commit.committer.date
        }
    }
}