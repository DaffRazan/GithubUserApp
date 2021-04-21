package com.daffa.consumerapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daffa.consumerapp.R
import com.daffa.consumerapp.adapter.SectionsPagerAdapter
import com.daffa.consumerapp.database.UserEntity
import com.daffa.consumerapp.databinding.ActivityDetailUserBinding
import com.daffa.consumerapp.viewModel.DetailActivityViewModel
import com.google.android.material.tabs.TabLayoutMediator
import de.hdodenhof.circleimageview.CircleImageView

class DetailUserActivity : AppCompatActivity() {

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

        userEntity = intent.getParcelableExtra<UserEntity>(EXTRA_USER)!!

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

//         favorite user
        userEntity.id?.let {
            viewModel.setFavoriteById(it, applicationContext)
        }

        viewModel.getFavoriteById().observe(this, {
            if (it.count >= 1) {
                statusFavorite = true
            }
        })

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

    // load avatar
    private fun CircleImageView.loadAvatar(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions().override(200, 200))
            .into(binding.imgAvatarReceived)
    }

    fun getToastOnFailure() {
        Toast.makeText(application, "Error when accessing an API", Toast.LENGTH_SHORT).show()
    }

}