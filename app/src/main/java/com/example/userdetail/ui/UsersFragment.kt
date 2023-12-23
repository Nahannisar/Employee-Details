package com.example.userdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userdetail.adapter.LoadMoreAdapter
import com.example.userdetail.adapter.UsersAdapter
import com.example.userdetail.databinding.FragmentUsersBinding
import com.example.userdetail.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private lateinit var binding: FragmentUsersBinding

    @Inject
    lateinit var usersAdapter: UsersAdapter
    private val viewModel: UsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentUsersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            lifecycleScope.launchWhenCreated {
                viewModel.usersList.collect {
                    usersAdapter.submitData(it)
                }
            }
            usersAdapter.setOnItemClickListener {
                val direction = UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment(it.id)
                findNavController().navigate(direction)
            }

            lifecycleScope.launchWhenCreated {
                usersAdapter.loadStateFlow.collect{
                    val state = it.refresh
                    prgBarUsers.isVisible = state is LoadState.Loading
                }
            }

            rlUsers.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = usersAdapter
            }

            rlUsers.adapter= usersAdapter.withLoadStateFooter(
                LoadMoreAdapter{
                    usersAdapter.retry()
                }
            )

        }
    }

}