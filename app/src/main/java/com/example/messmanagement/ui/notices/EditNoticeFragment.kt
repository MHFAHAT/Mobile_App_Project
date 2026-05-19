package com.example.messmanagement.ui.notices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.messmanagement.R
import com.example.messmanagement.data.repository.NoticeRepository

class EditNoticeFragment : Fragment() {

    private lateinit var repository: NoticeRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_edit_notice,
            container,
            false
        )

        repository =
            NoticeRepository(requireContext())

        val etTitle =
            view.findViewById<EditText>(R.id.etTitle)

        val etMessage =
            view.findViewById<EditText>(R.id.etMessage)

        val btnUpdateNotice =
            view.findViewById<Button>(R.id.btnUpdateNotice)

        val noticeId =
            arguments?.getInt("notice_id") ?: 0

        val title =
            arguments?.getString("title") ?: ""

        val message =
            arguments?.getString("message") ?: ""

        etTitle.setText(title)
        etMessage.setText(message)

        btnUpdateNotice.setOnClickListener {

            val updatedTitle =
                etTitle.text.toString().trim()

            val updatedMessage =
                etMessage.text.toString().trim()

            if (
                updatedTitle.isEmpty() ||
                updatedMessage.isEmpty()
            ) {

                Toast.makeText(
                    requireContext(),
                    "All fields are required",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val success =
                repository.updateNotice(
                    noticeId,
                    updatedTitle,
                    updatedMessage
                )

            if (success) {

                Toast.makeText(
                    requireContext(),
                    "Notice Updated",
                    Toast.LENGTH_SHORT
                ).show()

                parentFragmentManager.popBackStack()

            } else {

                Toast.makeText(
                    requireContext(),
                    "Update Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }
}