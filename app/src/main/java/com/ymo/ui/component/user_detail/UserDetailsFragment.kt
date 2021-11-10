package com.ymo.ui.component.user_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.ymo.data.Resource
import com.ymo.data.Status
import com.ymo.data.model.api.UserItem
import com.ymo.databinding.FragmentUserDetailsBinding
import com.ymo.utils.showSnackbar
import com.ymo.utils.toGone
import com.ymo.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserDetailsViewModel by viewModels()

    private val args: UserDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadUserDetails(args.userId)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.userItemLiveData.observe(viewLifecycleOwner, ::userDetailHandler)
    }


    private fun userDetailHandler(resource: Resource<UserItem>) {
        when (resource.status) {
            Status.LOADING -> binding.loaderView.toVisible()
            Status.SUCCESS -> resource.data?.let {
                binding.loaderView.toGone()
                bindDetailData(it)
            }
            Status.ERROR -> {
                binding.loaderView.toGone()
                resource.errorMessage?.let { binding.root.showSnackbar(it, Snackbar.LENGTH_LONG) }
            }
        }

    }

    private fun bindDetailData(userItem: UserItem) {
        binding.tvUsername.text = userItem.name
        binding.tvEmailAddress.text = userItem.email
        binding.tvPhoneNo.text = userItem.phone
        binding.tvStreet.text = userItem.address?.street
        binding.tvSuite.text = userItem.address?.suite
        binding.tvCity.text = userItem.address?.city
        binding.tvZipCode.text = userItem.address?.zipcode
        binding.tvWebsite.text = userItem.website
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}