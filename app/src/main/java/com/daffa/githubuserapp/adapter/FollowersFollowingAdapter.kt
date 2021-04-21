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
import com.daffa.githubuserapp.retrofitapi.model.User

class FollowersFollowingAdapter(private val context: Context) : RecyclerView.Adapter<FollowersFollowingAdapter.FollowersFollowingViewHolder>() {

    private var listUser: List<User> = arrayListOf()

    inner class FollowersFollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar : ImageView = itemView.findViewById(R.id.img_item_avatar)
        var tvUsername : TextView = itemView.findViewById(R.id.tv_item_username)
        var tvType : TextView = itemView.findViewById(R.id.tv_item_type)
    }

    fun setUserList(list: List<User>){
        this.listUser = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): FollowersFollowingViewHolder {
       val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_followersfollowing_user, viewGroup, false)
        return FollowersFollowingViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FollowersFollowingViewHolder,
        position: Int
    ) {
        val user = listUser[position]
        Glide.with(holder.imgAvatar.context)
            .load(user.avatar_url)
            .apply(RequestOptions().override(250, 250))
            .into(holder.imgAvatar)
        holder.tvUsername.text = user.login
        holder.tvType.text = user.type
    }

    override fun getItemCount(): Int = listUser.size
}