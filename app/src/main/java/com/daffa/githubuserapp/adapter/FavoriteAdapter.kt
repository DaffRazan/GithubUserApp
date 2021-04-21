package com.daffa.githubuserapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daffa.githubuserapp.R
import com.daffa.githubuserapp.database.UserEntity

class FavoriteAdapter(private val context: Context) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var listUser: ArrayList<UserEntity> = arrayListOf()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setUserList(list: ArrayList<UserEntity>) {
        this.listUser = list
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserEntity)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cardview_user, viewGroup, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val user = listUser[position]
        Glide.with(holder.imgAvatar.context)
            .load(user.avatar_url)
            .apply(RequestOptions().override(150, 150))
            .into(holder.imgAvatar)
        holder.tvUsername.text = user.login
        holder.tvType.text = user.type

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listUser.size

    inner class FavoriteViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.findViewById(R.id.img_item_avatar)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_item_username)
        var tvType: TextView = itemView.findViewById(R.id.tv_item_type)
    }
}