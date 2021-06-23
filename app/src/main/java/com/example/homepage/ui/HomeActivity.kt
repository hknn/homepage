package com.example.homepage.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.example.homepage.BuildConfig
import com.example.homepage.R
import com.example.homepage.databinding.ActivityHomeBinding
import com.example.homepage.ui.DetailActivity.Companion.PAGE_ID
import com.example.homepage.ui.adapter.HomeAdapter
import com.example.homepage.utils.isNetworkAvailable
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var homeBinding: ActivityHomeBinding

    private val adapter by lazy {
        HomeAdapter(this) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(PAGE_ID, it)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        homeBinding.rvHome.adapter = adapter

        if (isNetworkAvailable()) {
            homeViewModel.postInit(BuildConfig.VERSION_NAME)
            homeViewModel.getHome()
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }

        with(homeViewModel) {

            homeData.observe(this@HomeActivity, {
                homeBinding.progressBar.visibility = View.GONE
                val list = ArrayList<Any>()
                list.add(it)
                list.addAll(it.banners)
                adapter.addList(list)
            })

            messageData.observe(this@HomeActivity, {
                Toast.makeText(this@HomeActivity, it, LENGTH_LONG).show()
            })

            showProgressbar.observe(this@HomeActivity, { isVisible ->
                homeBinding.progressBar.visibility = if (isVisible) VISIBLE else GONE
            })
        }
    }
}