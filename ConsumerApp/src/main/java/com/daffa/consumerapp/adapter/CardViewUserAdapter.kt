package com.daffa.consumerapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daffa.consumerapp.R
import com.daffa.consumerapp.retrofitapi.model.User

class CardViewUserAdapter(private val context: Context) :
    RecyclerView.Adapter<CardViewUserAdapter.CardViewViewHolder>() {

    var listUser: List<User> = arrayListOf()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setDataUser(list: List<User>) {
        this.listUser = list
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.findViewById(R.id.img_item_avatar)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_item_username)
        var tvType: TextView = itemView.findViewById(R.id.tv_item_type)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cardview_user, viewGroup, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        val user = listUser[position]
        Glide.with(holder.imgAvatar.context)
            .load(user.avatar_url)
            .apply(RequestOptions().override(150, 150))
            .into(holder.imgAvatar)
        holder.tvUsername.text = user.login
        holder.tvType.text = user.type

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }
}


