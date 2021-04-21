package com.daffa.githubuserapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daffa.githubuserapp.R
import com.daffa.githubuserapp.adapter.SectionsPagerAdapter
import com.daffa.githubuserapp.database.MappingHelper
import com.daffa.githubuserapp.database.UserEntity
import com.daffa.githubuserapp.databinding.ActivityDetailUserBinding
import com.daffa.githubuserapp.viewModel.DetailActivityViewModel
import com.google.android.material.tabs.TabLayoutMediator
import de.hdodenhof.circleimageview.CircleImageView

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailActivityViewModel
    private var statusFavorite: Boolean = false
    private lateinit var userEntity: UserEntity

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userEntity = intent.getParcelableExtra(EXTRA_USER)!!

        // Settings
        binding.btnSettings.setOnClickListener(this)

        // Initialize View Model Provider
        viewModel = ViewModelProvider(this).get(DetailActivityViewModel::class.java)
        viewModel.getUserModel(userEntity.login.toString())

        // Observe live data
        viewModel.userDetailData.observe(this, {
            binding.imgAvatarReceived.loadAvatar(it.avatar_url)
            binding.detailTitle.text = StringBuilder("Detail ").append(it.login)
            binding.tvNameReceived.text = it.name
            binding.tvCompanyReceived.text = it.company
            binding.tvLocationReceived.text = it.location
            binding.tvRepositoryReceived.text = it.public_repos.toString()
            binding.tvFollowerReceived.text = it.followers.toString()
            binding.tvFollowingReceived.text = it.following.toString()
        })

        // Favorite user
        userEntity.id?.let {
            viewModel.setFavoriteById(it, applicationContext)
        }

        viewModel.getFavoriteById().observe(this, {
            if (it.count >= 1) {
                statusFavorite = true
            }
            setStatusFavorite(statusFavorite)
        })

        // Fab Click
        binding.fabFavorite.setOnClickListener(this)

        // Observe progressBar
        viewModel.progressBar.observe(this, {
            if (it) binding.progressBarDetail.visibility = View.VISIBLE
            else binding.progressBarDetail.visibility = View.GONE
        })

        //Set ViewPager2 and Tab Layout
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = userEntity.login
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    // Load Avatar
    private fun CircleImageView.loadAvatar(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions().override(200, 200))
            .into(binding.imgAvatarReceived)
    }

    fun getToastOnFailure() {
        Toast.makeText(application, "Error when accessing an API", Toast.LENGTH_SHORT).show()
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) binding.fabFavorite.setImageResource(R.drawable.ic_fill_favorite)
        else binding.fabFavorite.setImageResource(R.drawable.ic_unfill_favorite)
    }

    private fun setFavoriteUser(statusFavorite: Boolean) {
        if (statusFavorite) {
            val content = MappingHelper.convertToContentValues(userEntity)
            viewModel.setFavoriteUser(content, applicationContext)
            Toast.makeText(this, getString(R.string.add_favorite), Toast.LENGTH_SHORT).show()
        } else {
            userEntity.id?.let { viewModel.deleteFavoriteUser(it, applicationContext) }
            Toast.makeText(this, getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show()
        }
    }

    // Make favorite/unfavorite users and settings click
    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab_favorite -> {
                statusFavorite = !statusFavorite
                setFavoriteUser(statusFavorite)
                setStatusFavorite(statusFavorite)
            }
            R.id.btn_settings -> {
                val moveToSettingIntent = Intent(this@DetailUserActivity, SettingsActivity::class.java)
                startActivity(moveToSettingIntent)
            }
        }
    }


}