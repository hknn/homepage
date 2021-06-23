package com.example.homepage.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homepage.R
import com.example.homepage.data.Banner
import com.example.homepage.data.HomeModel
import com.example.homepage.data.ImageModel
import com.example.homepage.data.Slider
import com.example.homepage.ui.adapter.HomeAdapter.ViewType.*

class HomeAdapter(
    val context: Context,
    val click: ((Int) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = ArrayList<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            SLIDER.ordinal -> {
                SliderViewHolder(inflater.inflate(R.layout.item_slider, parent, false))
            }
            IMAGE.ordinal -> {
                ImageViewHolder(inflater.inflate(R.layout.item_image, parent, false))
            }
            PRODUCT_DETAIL.ordinal -> {
                DetailSliderViewHolder(inflater.inflate(R.layout.item_image, parent, false))
            }
            else -> {
                BannerViewHolder(inflater.inflate(R.layout.item_home, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BannerViewHolder -> {
                holder.bind(list[position] as Banner)
            }
            is SliderViewHolder -> {
                holder.bind(list[position] as HomeModel)
            }
            is ImageViewHolder -> {
                holder.bind(list[position] as Slider)
            }
            is DetailSliderViewHolder -> {
                holder.bind(list[position] as ImageModel)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is Banner) {
            BANNER.ordinal
        } else if (list[position] is HomeModel) {
            SLIDER.ordinal
        } else if (list[position] is ImageModel) {
            PRODUCT_DETAIL.ordinal
        } else {
            IMAGE.ordinal
        }
    }

    fun addList(items: List<Any>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: Banner) {
            val image = itemView.findViewById<ImageView>(R.id.iv_banner)
            Glide.with(itemView.context).load(model.image.url)
                .override(model.image.width, model.image.height)
                .into(image)
            image.setOnClickListener {
                click.invoke(model.id)
            }
        }
    }

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: Slider) {
            val sliderImage = itemView.findViewById<ImageView>(R.id.iv_slider)
            Glide.with(itemView.context).load(model.image.url)
                .into(sliderImage)
            sliderImage.setOnClickListener {
                click.invoke(model.id)
            }
        }
    }

    inner class DetailSliderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: ImageModel) {
            val sliderImage = itemView.findViewById<ImageView>(R.id.iv_slider)
            Glide.with(itemView.context).load(model.url).into(sliderImage)
        }
    }

    inner class SliderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: HomeModel) {
            val recylerView = itemView.findViewById<RecyclerView>(R.id.rv_slider)
            val adapter = HomeAdapter(itemView.context) {
                click.invoke(it)
            }
            recylerView.adapter = adapter
            adapter.addList(model.sliders)
        }
    }

    enum class ViewType {
        BANNER,
        IMAGE,
        SLIDER,
        PRODUCT_DETAIL
    }
}