package com.bmnv.assignment1.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import android.widget.ImageView
import com.bmnv.assignment1.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

object ImageLoader {

    private val memoryCache = LruCache<String, Bitmap>(calculateMemoryCacheSize())

    private fun calculateMemoryCacheSize(): Int {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        return maxMemory / 8
    }

    suspend fun loadImage(url: String, imageView: ImageView) {
        val bitmap = memoryCache.get(url)
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            try {
                val mBitmap = downloadImage(url)
                memoryCache.put(url, mBitmap)
                withContext(Dispatchers.Main) {
                    imageView.setImageBitmap(mBitmap)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    imageView.setImageResource(R.drawable.placeholder) // Placeholder image on error
                }
            }
        }
    }

    private suspend fun downloadImage(url: String): Bitmap = withContext(Dispatchers.IO) {
        val urlConnection = URL(url).openConnection() as HttpURLConnection
        urlConnection.connect()
        val inputStream = urlConnection.inputStream
        BitmapFactory.decodeStream(inputStream)
    }
}