package com.bmnv.assignment1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bmnv.assignment1.databinding.ItemImageBinding
import com.bmnv.assignment1.utils.ImageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImageAdapter(private val imageList: List<String>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    private lateinit var binding: ItemImageBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = imageList[position]
        holder.bind(imageUrl)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onViewRecycled(holder: ImageViewHolder) {
        super.onViewRecycled(holder)
        holder.cancelJob()
    }
    class ImageViewHolder(private val mItemView: ItemImageBinding) :
        RecyclerView.ViewHolder(mItemView.root) {
        private var job: Job? = null
        fun bind(url: String) {
            cancelJob()
            mItemView.imageView.setImageResource(R.drawable.placeholder)
            job = CoroutineScope(Dispatchers.Main).launch {
                ImageLoader.loadImage(url, mItemView.imageView)
            }
        }

        fun cancelJob() {
            job?.cancel()
        }
    }
}
