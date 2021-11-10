package com.ymo.data.remote.service

import com.ymo.data.model.api.UserItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    suspend fun loadUserList(): List<UserItem>

    @GET("users/{user_id}")
    suspend fun loadUserDetails(
        @Path("user_id") userId: Int,
    ): UserItem
}
