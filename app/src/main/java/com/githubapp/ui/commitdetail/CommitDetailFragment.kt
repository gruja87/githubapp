package com.githubapp.ui.commitdetail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.githubapp.R
import com.githubapp.data.models.Commit
import com.githubapp.util.formatDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_commit_detail.*
import javax.inject.Inject

@AndroidEntryPoint
class CommitDetailFragment : Fragment(R.layout.fragment_commit_detail) {

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parseData()
    }

    private fun parseData() {
        val sha = requireArguments().getString("commit_sha")

        sha?.let {
            tv_commit_detail_desc.text = sha
        }
    }

    private fun setupUI(commit: Commit) {
        tv_commit_detail_desc.text = commit.commit?.message
        tv_commit_detail_commiter.text = String.format(
            getString(
                R.string.format_commiter_text,
                commit.commit?.committer?.name,
                formatDate(commit.commit?.committer?.date ?: "")
            )
        )

        tv_commit_detail_commit_sha.text = String.format(
            getString(R.string.format_commit_sha),
            commit.sha
        )

        requestManager
            .load(commit.committer?.avatar_url)
            .into(iv_commit_detail_commiter_avatar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController()
                    .navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
