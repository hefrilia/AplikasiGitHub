package com.example.aplikasigithub.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplikasigithub.data.response.UserItems
import com.example.aplikasigithub.databinding.ItemReviewBinding
import com.google.gson.Gson

class UserAdapter : ListAdapter<UserItems, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        val userJson = Gson().toJson(user)
        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.MAIN_INTENT_KEY, userJson)
            holder.itemView.context.startActivity(intentDetail)
        }
        holder.bind(user)
    }
    class MyViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: UserItems){
            binding.tvUsername.text = review.login
            binding.tvItemDescription.text = "id : ${review.id}"
            Glide.with(binding.root)
                .load(review.avatarUrl)
                .into(binding.imgUserPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserItems>() {
            override fun areItemsTheSame(oldItem: UserItems, newItem: UserItems): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: UserItems, newItem: UserItems): Boolean {
                return oldItem == newItem
            }
        }
    }




}