package com.ymo.ui.component.user_list

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
class UserListViewModel @Inject constructor(
    private val dataRepositoryHelper: DataRepositoryHelper
) : BaseViewModel() {
    private val userList = MutableLiveData<Resource<List<UserItem>>>()
    val userListLiveData: LiveData<Resource<List<UserItem>>> get() = userList

    fun loadMovies() {
        if (network.isConnected) {
            viewModelScope.launch {
                userList.postValue(Resource.loading(null))
                try {
                    userList.postValue(Resource.success(dataRepositoryHelper.loadUserList()))
                } catch (e: HttpException) {
                    userList.postValue(Resource.error(errorManager.getHttpError(e).description, null))
                } catch (e: Exception) {
                    userList.postValue(Resource.error(e.localizedMessage ?: e.message!!, null))
                }

            }
        } else userList.postValue(
            Resource.error(
                errorManager.getError(NETWORK_ERROR).description,
                null
            )
        )
    }


}