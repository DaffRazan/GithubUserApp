package com.daffa.githubuserapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffa.githubuserapp.R
import com.daffa.githubuserapp.adapter.CardViewUserAdapter
import com.daffa.githubuserapp.database.UserEntity
import com.daffa.githubuserapp.databinding.ActivityMainBinding
import com.daffa.githubuserapp.retrofitapi.model.User
import com.daffa.githubuserapp.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CardViewUserAdapter
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Settings and Favorite buttons
        binding.btnSettings.setOnClickListener(this)
        binding.btnFavoriteActivity.setOnClickListener(this)

        // Initialize View Model Provider
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        // Search User
        searchUser()

        // Show loading when retrieve data
        showLoading()

        // Show Recycler View
        binding.rvUsers.setHasFixedSize(true)
        showRecyclerCardView()
    }

    private fun searchUser() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isNotEmpty()) {
                    mainActivityViewModel.searchUserModel(query.toString())
                    binding.searchView.clearFocus()
                    searchNotFound(false)
                } else {
                    binding.searchView.clearFocus()
                    searchNotFound(true)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun searchNotFound(state: Boolean) {
        if (state) {
            binding.rvUsers.visibility = View.GONE
            binding.userNotFound.visibility = View.VISIBLE
            binding.textNotFound1.visibility = View.VISIBLE
            binding.textNotFound2.visibility = View.VISIBLE
        } else {
            binding.rvUsers.visibility = View.VISIBLE
            binding.userNotFound.visibility = View.GONE
            binding.textNotFound1.visibility = View.GONE
            binding.textNotFound2.visibility = View.GONE
        }
    }

    private fun showLoading() {
        // observe progress bar
        mainActivityViewModel.progressBar.observe(this, {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })
    }

    private fun showSelectUser(user: User) {
        val moveWithObjectIntent = Intent(this@MainActivity, DetailUserActivity::class.java)

        val userEntity = UserEntity()
        userEntity.id = user.id
        userEntity.login = user.login
        userEntity.type = user.type
        userEntity.avatar_url = user.avatar_url
        moveWithObjectIntent.putExtra(DetailUserActivity.EXTRA_USER, userEntity)
        startActivity(moveWithObjectIntent)
    }

    private fun showRecyclerCardView() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        adapter = CardViewUserAdapter(this)
        mainActivityViewModel.listUser.observe(this, {
            adapter.setDataUser(it)
            searchNotFound(it.isEmpty())
        })
        binding.rvUsers.adapter = adapter

        adapter.setOnItemClickCallback(object :
            CardViewUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectUser(data)
            }
        })
    }

    fun getToastOnFailure() {
        Toast.makeText(application, "Error when accessing an API", Toast.LENGTH_SHORT).show()
    }

    // Settings
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_settings -> {
                val moveToSettingIntent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(moveToSettingIntent)
            }
            R.id.btn_favorite_activity -> {
                val moveToFavoriteActivity = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(moveToFavoriteActivity)
            }
        }

    }
}