package com.daffa.githubuserapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffa.githubuserapp.adapter.FollowersFollowingAdapter
import com.daffa.githubuserapp.databinding.FragmentFollowersBinding
import com.daffa.githubuserapp.viewModel.FollowersFragmentViewModel


/**
 * Followers Fragment
 *
 */
class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowersFragmentViewModel

    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String) : FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initiliaze View Model
        viewModel = ViewModelProvider(this).get(FollowersFragmentViewModel::class.java)
        viewModel.getFollowersModel(arguments?.getString(ARG_USERNAME).toString())

        // Call recycler view followers
        binding.rvFollowersUser.setHasFixedSize(true)
        showRecyclerFollowersFollowingView()

        // Progress Bar
        showLoading()
    }

    private fun showLoading() {
        viewModel.progressBar.observe(requireActivity(), {
            if (it) binding.progressBarFollowers.visibility = View.VISIBLE
            else binding.progressBarFollowers.visibility = View.GONE
        })
    }

    private fun setNoFoundPeople(state: Boolean){
        binding.rvFollowersUser.visibility = if (state) View.GONE else View.VISIBLE
        binding.userNotFound.visibility = if (state) View.VISIBLE else View.GONE
        binding.textNotFound1.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showRecyclerFollowersFollowingView() {
        binding.rvFollowersUser.layoutManager = LinearLayoutManager(activity)
        val followersAdapter = FollowersFollowingAdapter(requireActivity())
        viewModel.listFollowers.observe(requireActivity(), {
            followersAdapter.setUserList(it)
            setNoFoundPeople(it.isEmpty())
        })
        binding.rvFollowersUser.adapter = followersAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}