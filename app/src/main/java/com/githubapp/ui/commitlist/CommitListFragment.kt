package com.githubapp.ui.commitlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githubapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_commits_list.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CommitListFragment : Fragment(R.layout.fragment_commits_list) {

    private val viewModel: CommitListViewModel by viewModels()

    private lateinit var commitListAdapter: CommitListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.commit_list)

        setupRecyclerView()
        subscribeObservers()
        parseData()
    }

    private fun subscribeObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.stateEvent.collect { event ->
                when (event) {
                    is CommitListViewModel.CommitsEvent.Success -> {
                        pb_commit_list.visibility = View.INVISIBLE
                        commitListAdapter.addCommitList(event.commitsList)
                    }

                    is CommitListViewModel.CommitsEvent.Error -> {
                        pb_commit_list.visibility = View.INVISIBLE
                        Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    }

                    is CommitListViewModel.CommitsEvent.Loading -> {
                        pb_commit_list.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        rv_commit_list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            commitListAdapter = CommitListAdapter()
            adapter = commitListAdapter
        }
    }

    private fun parseData(){
        val owner = requireArguments().getString("owner")
        val repo = requireArguments().getString("repo")

        if (!owner.isNullOrEmpty() && !repo.isNullOrEmpty()){
            viewModel.getCommitList(owner, repo)
        }
    }
}