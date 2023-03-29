package com.example.submissiongithub.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissiongithub.Model.Users
import com.example.submissiongithub.databinding.ItemUsersBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private val listUser = ArrayList<Users>()
    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder((view))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindItem(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(user: Users) {

            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                tvUsersUsernames.text = user.login
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .centerCrop()
                    .into(usersProfpic)

            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(users: ArrayList<Users>) {
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    fun setOnitemClikCallback(onItemClickCallback: UserAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback

    }
}