package com.ymo.ui.component.user_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ymo.data.DataRepositoryHelper
import com.ymo.data.Resource
import com.ymo.data.model.api.UserItem
import com.ymo.data.model.error.NETWORK_ERROR
import com.ymo.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val dataRepositoryHelper: DataRepositoryHelper
) : BaseViewModel() {
    private val userItem = MutableLiveData<Resource<UserItem>>()
    val userItemLiveData: LiveData<Resource<UserItem>> get() = userItem


    fun loadUserDetails(userId: Int) {
        if (network.isConnected) {
            viewModelScope.launch {
                userItem.postValue(Resource.loading(null))
                try {
                    userItem.postValue(
                        Resource.success(
                            dataRepositoryHelper.loadUserDetails(
                                userId
                            )
                        )
                    )
                } catch (e: HttpException) {
                    userItem.postValue(
                        Resource.error(
                            errorManager.getHttpError(e).description,
                            null
                        )
                    )
                } catch (e: Exception) {
                    userItem.postValue(Resource.error(e.localizedMessage ?: e.message!!, null))
                }

            }
        } else userItem.postValue(
            Resource.error(
                errorManager.getError(NETWORK_ERROR).description,
                null
            )
        )
    }

}

