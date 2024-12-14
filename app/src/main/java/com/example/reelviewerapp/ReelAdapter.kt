package com.example.reelviewerapp
import android.os.Bundle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ReelAdapter(
    fragmentActivity: FragmentActivity,
    private val videoDataList: List<VideoData>
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = videoDataList.size

    override fun createFragment(position: Int): Fragment {
        val fragment = ReelFragment()
        fragment.arguments = Bundle().apply {
            putString("video_url", videoDataList[position].url)
            putString("caption", videoDataList[position].caption)
            putInt("position", position)
        }
        return fragment
    }
}