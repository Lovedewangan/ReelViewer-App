package com.example.reelviewerapp

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class CommentDialog : DialogFragment() {
    private var commentListener: ((String) -> Unit)? = null

    fun setCommentListener(listener: (String) -> Unit) {
        commentListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_comment, null)
        val commentEdit = view.findViewById<EditText>(R.id.commentEdit)

        return builder.setView(view)
            .setTitle("Add Comment")
            .setPositiveButton("Post") { _, _ ->
                val comment = commentEdit.text.toString()
                if (comment.isNotEmpty()) {
                    commentListener?.invoke(comment)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}