package com.githubapp.ui.commitlist

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.githubapp.R
import com.githubapp.data.models.Commit
import com.githubapp.util.Constants.TAG_COMMIT
import com.githubapp.util.Constants.TAG_OWNER
import com.githubapp.util.Constants.TAG_REPO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_commits_list.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class CommitListFragment : Fragment(R.layout.fragment_commits_list),
    CommitListAdapter.OnCommitClickListener {

    private val viewModel: CommitListViewModel by viewModels()

    private lateinit var commitListAdapter: CommitListAdapter

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).apply {
            supportActionBar?.title = getString(R.string.commit_list)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

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
                        tv_commit_num.text = String.format(
                            getString(R.string.format_commits_num),
                            event.commitsList.size
                        )
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
            commitListAdapter = CommitListAdapter(
                requestManager = requestManager,
                onCommitClickListener = this@CommitListFragment
            )
            adapter = commitListAdapter
        }
    }

    private fun parseData() {
        val owner = requireArguments().getString(TAG_OWNER)
        val repo = requireArguments().getString(TAG_REPO)

        if (!owner.isNullOrEmpty() && !repo.isNullOrEmpty()) {
            (requireActivity() as AppCompatActivity).supportActionBar?.title = repo
            viewModel.getCommitList(owner, repo)
        }
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

    override fun commitClicked(commit: Commit) {
        val bundle = Bundle()
        bundle.putParcelable(TAG_COMMIT, commit)
        findNavController()
            .navigate(R.id.commitDetailFragment, bundle)
    }
}