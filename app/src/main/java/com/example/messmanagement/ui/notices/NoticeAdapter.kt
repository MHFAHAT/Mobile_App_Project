package com.example.messmanagement.ui.notices

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagement.R
import com.example.messmanagement.data.model.Notice

class NoticeAdapter(
    private val noticeList: List<Notice>,
    private val onItemClick: (Notice) -> Unit,
    private val onItemLongClick: (Notice) -> Unit,
) : RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {

    class NoticeViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        val tvTitle: TextView =
            view.findViewById(R.id.tvTitle)

        val tvMessage: TextView =
            view.findViewById(R.id.tvMessage)

        val tvTimestamp: TextView =
            view.findViewById(R.id.tvTimestamp)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoticeViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_notice,
                parent,
                false
            )

        return NoticeViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NoticeViewHolder,
        position: Int
    ) {

        val notice = noticeList[position]

        holder.tvTitle.text = notice.title
        holder.tvMessage.text = notice.message
        holder.tvTimestamp.text = notice.timestamp
        holder.itemView.setOnClickListener {

            onItemClick(notice)
        }

        holder.itemView.setOnLongClickListener {

            onItemLongClick(notice)

            true
        }
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }
}