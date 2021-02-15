package com.githubapp.ui.repolist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githubapp.R
import com.githubapp.data.models.Repo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repos_list.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RepoListFragment : Fragment(R.layout.fragment_repos_list),
    RepoListAdapter.OnRepoClickListener {

    private val viewModel: RepoListViewModel by viewModels()

    private lateinit var reposListAdapter: RepoListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.repo_list)

        setupRecyclerView()

        subscribeObservers()

        viewModel.getReposList()
    }

    private fun setupRecyclerView() {
        rv_repos_list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            reposListAdapter = RepoListAdapter(onRepoClickListener = this@RepoListFragment)
            adapter = reposListAdapter
        }
    }

    private fun subscribeObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.stateEvent.collect { event ->
                when (event) {
                    is RepoListViewModel.ReposEvent.Success -> {
                        pb_repos_list.visibility = View.INVISIBLE
                        reposListAdapter.addReposList(event.reposList)
                    }

                    is RepoListViewModel.ReposEvent.Error -> {
                        pb_repos_list.visibility = View.INVISIBLE
                        Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    }

                    is RepoListViewModel.ReposEvent.Loading -> {
                        pb_repos_list.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun repoClicked(repo: Repo) {
        val bundle = Bundle()
        bundle.putString("owner", repo.owner.login)
        bundle.putString("repo", repo.name)
        findNavController()
            .navigate(
                R.id.commitListFragment,
                bundle
            )
    }
}
