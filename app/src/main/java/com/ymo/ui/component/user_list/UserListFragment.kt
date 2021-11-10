package com.ymo.ui.component.user_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ymo.data.Resource
import com.ymo.data.Status
import com.ymo.data.model.api.UserItem
import com.ymo.databinding.FragmentUserListBinding
import com.ymo.utils.showSnackbar
import com.ymo.utils.toGone
import com.ymo.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment(), UpcomingMoviesAdapter.OnClickedListener {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserListViewModel by viewModels()

    private val userListAdaper: UpcomingMoviesAdapter by lazy {
        UpcomingMoviesAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUIs()
        setupObservers()
    }

    private fun setupUIs() {
        viewModel.loadMovies()
        binding.rvUserList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = userListAdaper
        }

        binding.btnNoData.setOnClickListener {
            binding.loaderView.toVisible()
            binding.btnNoData.toGone()
            viewModel.loadMovies()
        }

    }

    private fun setupObservers() {
        viewModel.userListLiveData.observe(viewLifecycleOwner, ::userListHandler)
    }

    private fun userListHandler(resource: Resource<List<UserItem>>) {
        when (resource.status) {
            Status.LOADING -> showLoadingView()
            Status.SUCCESS -> resource.data?.let {
                showDataView(true)
                userListAdaper.submitList(it)
            }
            Status.ERROR -> {
                showDataView(false)
                resource.errorMessage?.let { binding.root.showSnackbar(it, Snackbar.LENGTH_LONG) }
            }
        }

    }

    override fun onClicked(userItem: UserItem) {
        findNavController().navigate(
            UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment(userItem.id)
        )
    }

    private fun showDataView(show: Boolean) {
        binding.btnNoData.visibility = if (show) View.GONE else View.VISIBLE
        binding.loaderView.toGone()
    }

    private fun showLoadingView() {
        binding.loaderView.toVisible()
        binding.btnNoData.toGone()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}