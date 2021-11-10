package com.ymo.data.remote

import com.ymo.data.model.api.UserItem
import com.ymo.data.remote.service.ApiService
import javax.inject.Inject

class ApiHelperImpl @Inject
constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun loadUserList(): List<UserItem> = apiService.loadUserList()
    override suspend fun loadUserDetails(userId: Int): UserItem = apiService.loadUserDetails(userId)

}
