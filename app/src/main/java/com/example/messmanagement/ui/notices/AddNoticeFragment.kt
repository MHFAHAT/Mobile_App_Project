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

class AddNoticeFragment : Fragment() {

    private lateinit var repository: NoticeRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_add_notice,
            container,
            false
        )

        repository =
            NoticeRepository(requireContext())

        val etTitle =
            view.findViewById<EditText>(R.id.etTitle)

        val etMessage =
            view.findViewById<EditText>(R.id.etMessage)

        val btnSaveNotice =
            view.findViewById<Button>(R.id.btnSaveNotice)

        btnSaveNotice.setOnClickListener {

            val title =
                etTitle.text.toString().trim()

            val message =
                etMessage.text.toString().trim()

            if (
                title.isEmpty() ||
                message.isEmpty()
            ) {

                Toast.makeText(
                    requireContext(),
                    "All fields are required",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val success =
                repository.addNotice(
                    title,
                    message
                )

            if (success) {

                Toast.makeText(
                    requireContext(),
                    "Notice Added Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                parentFragmentManager.popBackStack()

            } else {

                Toast.makeText(
                    requireContext(),
                    "Failed to add notice",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }
}