package com.example.reelviewerapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.reelviewerapp.databinding.FragmentReelBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem


class ReelFragment : Fragment() {
    private var _binding: FragmentReelBinding? = null
    private val binding get() = _binding!!
    private lateinit var player: ExoPlayer
    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt("position") ?: 0

        initPlayer()
        playVideo()
        setupSocialFeatures()

        // Set caption text
        val caption = arguments?.getString("caption") ?: "No Caption Available"
        binding.captionTextView.text = caption
    }

    private fun setupSocialFeatures() {
        // Like button click listener
        binding.likeButton.setOnClickListener {
            val currentVideo = (activity as MainActivity).videoDataList[position]
            if (!currentVideo.isLiked) {
                currentVideo.likes++
                currentVideo.isLiked = true
                binding.likeButton.setImageResource(R.drawable.ic_liked)
            } else {
                currentVideo.likes--
                currentVideo.isLiked = false
                binding.likeButton.setImageResource(R.drawable.ic_like)
            }
            binding.likesCount.text = "${currentVideo.likes} likes"
        }

        // Comment button click listener
        binding.commentButton.setOnClickListener {
            showCommentDialog()
        }

        // Share button click listener
        binding.shareButton.setOnClickListener {
            shareVideo()
        }
    }

    private fun showCommentDialog() {
        val dialog = CommentDialog()
        dialog.setCommentListener { comment ->
            val currentVideo = (activity as MainActivity).videoDataList[position]
            currentVideo.comments.add(Comment("user123", comment))
            updateCommentCount()
        }
        dialog.show(childFragmentManager, "CommentDialog")
    }

    private fun updateCommentCount() {
        val currentVideo = (activity as MainActivity).videoDataList[position]
        binding.commentsCount.text = "${currentVideo.comments.size} comments"
    }

    private fun shareVideo() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this awesome video!")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(requireContext()).build()
        binding.videoView.player = player
    }

    private fun playVideo() {
        val videoUrl = arguments?.getString("video_url") ?: return
        val mediaItem = MediaItem.fromUri(videoUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
    }

    override fun onStart() {
        super.onStart()
        player.play()
        Log.d("ReelFragment", "onStart: Video Started")
    }

    override fun onStop() {
        super.onStop()
        player.pause()
        Log.d("ReelFragment", "onStop: Video Paused")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.release()
        _binding = null
        Log.d("ReelFragment", "onDestroyView: Video Released")
    }
}
