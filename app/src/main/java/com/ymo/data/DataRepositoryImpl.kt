package com.ymo.data

import com.ymo.data.model.api.UserItem
import com.ymo.data.remote.ApiHelper
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
) : DataRepositoryHelper {
    override suspend fun loadUserList(): List<UserItem> = apiHelper.loadUserList()
    override suspend fun loadUserDetails(userId: Int): UserItem = apiHelper.loadUserDetails(userId)

}
