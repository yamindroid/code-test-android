package com.ymo.ui.component.user_list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ymo.R
import com.ymo.data.model.api.UserItem
import com.ymo.utils.inflate
import kotlinx.android.synthetic.main.user_card.view.*

class UpcomingMoviesAdapter(
    private val listener: OnClickedListener
) : ListAdapter<UserItem, PopularViewHolder>(
    object : DiffUtil.ItemCallback<UserItem>() {
        override fun areItemsTheSame(
            oldItem: UserItem,
            newItem: UserItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserItem,
            newItem: UserItem
        ): Boolean {
            return oldItem == newItem
        }

    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(parent.inflate(R.layout.user_card))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    interface OnClickedListener {
        fun onClicked(userItem: UserItem)
    }
}

class PopularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        userItem: UserItem,
        listener: UpcomingMoviesAdapter.OnClickedListener
    ) {
        with(itemView)
        {
            tv_username.text = userItem.name
            tv_email_address.text = userItem.email

            setOnClickListener {
                listener.onClicked(userItem)
            }

        }

    }
}