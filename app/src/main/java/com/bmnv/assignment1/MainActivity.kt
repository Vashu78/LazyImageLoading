package com.bmnv.assignment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bmnv.assignment1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.adapter = ImageAdapter(images())
    }

    private fun images(): List<String> {
        val imageList = ArrayList<String>()
        for (i in 1..100) {
            imageList.add("https://fakeimg.pl/250x250/?text=$i")
        }
        return imageList
    }
}