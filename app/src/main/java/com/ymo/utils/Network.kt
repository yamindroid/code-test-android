package com.ymo.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Injectable class which returns information about the network connection state.
 */
@Singleton
class Network
@Inject constructor(@ApplicationContext  val context: Context) {
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting ?: false
}