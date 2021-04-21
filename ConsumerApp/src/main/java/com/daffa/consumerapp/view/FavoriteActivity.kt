package com.daffa.consumerapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffa.consumerapp.adapter.FavoriteAdapter
import com.daffa.consumerapp.database.UserEntity
import com.daffa.consumerapp.databinding.ActivityFavoriteBinding
import com.daffa.consumerapp.viewModel.FavoriteActivityViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var userAdapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteActivityViewModel
    private lateinit var binding: ActivityFavoriteBinding

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteViewModel = ViewModelProvider(this).get(FavoriteActivityViewModel::class.java)
        favoriteViewModel.setFavoriteListUser(applicationContext)

        binding.rvUsers.setHasFixedSize(true)

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        userAdapter = FavoriteAdapter(this)

        getListFavorite()

        userAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserEntity) {
                val moveIntent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                moveIntent.putExtra(DetailUserActivity.EXTRA_USER, data)
                startActivity(moveIntent)
            }
        })
    }

    private fun getListFavorite() {
        favoriteViewModel.getListFavorite().observe(this, {
            if (it != null) {
                userAdapter.setUserList(it)
                binding.rvUsers.adapter = userAdapter
            }

            if (it.isNotEmpty()) {
                binding.userNotFound.visibility = View.GONE
                binding.textNotFound1.visibility = View.GONE
                binding.rvUsers.visibility = View.VISIBLE
            } else {
                binding.userNotFound.visibility = View.VISIBLE
                binding.textNotFound1.visibility = View.VISIBLE
                binding.rvUsers.visibility = View.GONE
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, userAdapter.listUser)
    }

    override fun onResume() {
        super.onResume()
        getListFavorite()
    }
}