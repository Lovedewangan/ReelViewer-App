package com.example.reelviewerapp

data class VideoData(
    val url: String,
    val caption: String,
    var likes: Int = 0,
    val comments: ArrayList<Comment> = ArrayList(),
    var isLiked: Boolean = false
)

data class Comment(
    val userId: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)