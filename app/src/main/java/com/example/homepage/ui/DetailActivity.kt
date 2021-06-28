package com.example.homepage.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homepage.R
import com.example.homepage.databinding.ActivityDetailBinding
import com.example.homepage.ui.adapter.HomeAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var detailBinding: ActivityDetailBinding
    private var pageId: Int? = null

    private val adapter by lazy {
        HomeAdapter(this) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)
        intent.extras?.let { pageId = it.getInt(PAGE_ID) }
        setBackButton("$pageId")
        detailBinding.rvDetail.adapter = adapter

        pageId?.let { detailViewModel.getDetail(it) }

        with(detailViewModel) {

            detailData.observe(this@DetailActivity, {
                adapter.addList(it.images)
                detailBinding.tvTitle.text = it.title
                detailBinding.tvSubtitle.text = it.subTitle
                detailBinding.tvDescription.text = it.description
                detailBinding.tvPrice.text =
                    getString(R.string.price_placeholder, it.discountedPrice.toString())
            })

            messageData.observe(this@DetailActivity, {
                Toast.makeText(this@DetailActivity, it, Toast.LENGTH_LONG).show()
            })

            showProgressbar.observe(this@DetailActivity, { isVisible ->
                detailBinding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
            })
        }
    }

    fun setBackButton(title: String) {
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val PAGE_ID = "PAGE_ID"
    }
}