package com.example.messmanagement.ui.notices

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagement.R
import com.example.messmanagement.data.repository.NoticeRepository
import com.example.messmanagement.session.SessionManager
import com.example.messmanagement.ui.notices.AddNoticeFragment


class NoticesFragment : Fragment(R.layout.fragment_notices) {

    private lateinit var recyclerNotices: RecyclerView
    private lateinit var btnAddNotice: Button
    private lateinit var repository: NoticeRepository
    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        recyclerNotices =
            view.findViewById(R.id.recyclerNotices)

        btnAddNotice =
            view.findViewById(R.id.btnAddNotice)

        repository =
            NoticeRepository(requireContext())

        sessionManager =
            SessionManager(requireContext())

        recyclerNotices.layoutManager =
            LinearLayoutManager(requireContext())

        val role = sessionManager.getUserRole()

        if (role == "Resident") {
            btnAddNotice.visibility = View.GONE
        }
        btnAddNotice.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.home_fragment_container,
                    AddNoticeFragment()
                )
                .addToBackStack(null)
                .commit()
        }

        loadNotices()
    }

    override fun onResume() {
        super.onResume()
        loadNotices()
    }

    private fun loadNotices() {

        val noticeList =
            repository.getAllNotices()

        recyclerNotices.adapter =
            NoticeAdapter(noticeList)
    }
}