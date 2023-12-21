package com.example.aplikasigithub.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplikasigithub.data.response.UserItems
import com.example.aplikasigithub.database.FavoriteNote
import com.example.aplikasigithub.databinding.ItemFavoriteBinding
import com.example.aplikasigithub.helper.FavoriteDiffCallback
import com.google.gson.Gson

class FavoriteUserAdapter: RecyclerView.Adapter<FavoriteUserAdapter.FavUserViewHolder>() {
    private  val listNotes = ArrayList<FavoriteNote>()

    interface OnItemClickCallback {
        fun OnItemClicked(data: FavoriteNote)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setListNotes(listNotes: List<FavoriteNote>){
        val diffCallback = FavoriteDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavUserViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavUserViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }
    inner class FavUserViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(favoriteNote: FavoriteNote){
            with(binding){
                tvUsername.text = favoriteNote.namaUser
                Glide.with(root)
                    .load(favoriteNote.avatarUrl)
                    .into(imgUserPhoto)
                val user = UserItems(login = favoriteNote.namaUser, avatarUrl = favoriteNote.avatarUrl)
                val userJson = Gson().toJson(user)

                btnUnFavorite.setOnClickListener{
                    onItemClickCallback.OnItemClicked(favoriteNote)
                }

                cardView.setOnClickListener{
                    val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.MAIN_INTENT_KEY, userJson)
                    itemView.context.startActivity(intentDetail)
                }
            }
        }
    }
}