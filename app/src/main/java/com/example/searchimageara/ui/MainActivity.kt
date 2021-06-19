package com.example.searchimageara.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.searchimageara.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SearchImageara)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inflater = navHostFragment.findNavController().navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        navHostFragment.findNavController().graph = graph

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.navHostFragment).navigateUp()


    override fun setupNavDrawer(toolbar: androidx.appcompat.widget.Toolbar) {
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        drawerLayout.navView.setupWithNavController(navHostFragment.findNavController())

    }
    override fun enableNavDrawer(enable: Boolean) {
        drawerLayout.isEnabled = enable
    }
}