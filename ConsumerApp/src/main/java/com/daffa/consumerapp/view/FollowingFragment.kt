package com.daffa.consumerapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffa.consumerapp.adapter.FollowersFollowingAdapter
import com.daffa.consumerapp.databinding.FragmentFollowingBinding
import com.daffa.consumerapp.viewModel.FollowingFragmentViewModel

/**
 * Following Fragment
 *
 */
class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowingFragmentViewModel

    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String) : FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initiliaze View Model
        viewModel = ViewModelProvider(this)[FollowingFragmentViewModel::class.java]
        viewModel.getFollowingModel(arguments?.getString(ARG_USERNAME).toString())

        // Call recycler view following
        binding.rvFollowingUser.setHasFixedSize(true)
        showRecyclerFollowersFollowingView()

        // Progress Bar
        showLoading()
    }

    private fun showLoading() {
        viewModel.progressBar.observe(requireActivity(), {
            if (it) binding.progressBarFollowing.visibility = View.VISIBLE
            else binding.progressBarFollowing.visibility = View.GONE
        })
    }

    private fun setNoFoundPeople(state: Boolean){
        binding.rvFollowingUser.visibility = if (state) View.GONE else View.VISIBLE
        binding.userNotFound.visibility = if (state) View.VISIBLE else View.GONE
        binding.textNotFound1.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showRecyclerFollowersFollowingView() {
        binding.rvFollowingUser.layoutManager = LinearLayoutManager(activity)
        val followersAdapter = FollowersFollowingAdapter(requireActivity())
        viewModel.listFollowing.observe(requireActivity(), {
            followersAdapter.setUserList(it)
            setNoFoundPeople(it.isEmpty())
        })
        binding.rvFollowingUser.adapter = followersAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}