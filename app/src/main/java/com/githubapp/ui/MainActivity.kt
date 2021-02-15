package com.githubapp.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.githubapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation_view.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.main_graph, true)
            .build()
        when (menuItem.itemId) {
            R.id.repos_list -> {
                findNavController(R.id.main_nav_host_fragment)
                    .navigate(R.id.reposListFragment, null, navOptions)
                return true
            }
            R.id.profile -> {
                findNavController(R.id.main_nav_host_fragment)
                    .navigate(R.id.profileFragment, null, navOptions)
                return true
            }
        }
        return false
    }
}
