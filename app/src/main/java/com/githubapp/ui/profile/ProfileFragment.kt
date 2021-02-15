package com.githubapp.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.githubapp.R
import com.githubapp.data.models.Profile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    @Inject
    lateinit var requestManager: RequestManager

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).apply {
            supportActionBar?.title = getString(R.string.profile)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }


        subscribeObservers()

        viewModel.getProfile()
    }

    private fun subscribeObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.stateEvent.collect { event ->
                when (event) {
                    is ProfileViewModel.ProfileEvent.Success -> {
                        pb_profile.visibility = View.INVISIBLE
                        setupProfileUI(event.profile)
                    }

                    is ProfileViewModel.ProfileEvent.Error -> {
                        pb_profile.visibility = View.INVISIBLE
                        Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    }

                    is ProfileViewModel.ProfileEvent.Loading -> {
                        pb_profile.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupProfileUI(profile: Profile) {
        tv_profile_name.text = profile.name
        tv_profile_company.text = profile.company
        requestManager
            .load(profile.avatar_url)
            .into(iv_profile_avatar)
    }
}
