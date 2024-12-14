package com.example.reelviewerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.reelviewerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Make videoDataList public and accessible
    val videoDataList = mutableListOf<VideoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize video data
        videoDataList.addAll(listOf(
            VideoData("https://www.w3schools.com/html/mov_bbb.mp4", "This is the caption for Video 1"),
            VideoData("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4", "This is the caption for Video 2"),
            VideoData("https://www.w3schools.com/html/mov_bbb.mp4", "This is the caption for Video 3")
        ))

        val adapter = ReelAdapter(this, videoDataList)
        binding.viewPager.adapter = adapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
    }

    // Alternative approach: Add a method to get video data
    fun getVideoData(position: Int): VideoData {
        return videoDataList[position]
    }
}