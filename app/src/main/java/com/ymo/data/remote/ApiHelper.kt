package com.ymo.data.remote

import com.ymo.data.model.api.UserItem

interface ApiHelper {
    suspend fun loadUserList(): List<UserItem>
    suspend fun loadUserDetails(userId: Int): UserItem
}
