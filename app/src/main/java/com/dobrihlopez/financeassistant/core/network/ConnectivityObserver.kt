package com.dobrihlopez.financeassistant.core.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ConnectivityObserver {
    fun observe(): Flow<Boolean>

    class DefaultConnectivityObserver @Inject constructor(
        private val connectivityManager: ConnectivityManager,
    ) : ConnectivityObserver {

        private fun isNetworkAvailable(): Boolean {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        }

        override fun observe(): Flow<Boolean> {
            return callbackFlow {
                val callback = object : ConnectivityManager.NetworkCallback() {
                    init {
                        launch { send(isNetworkAvailable()) }
                    }

                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        launch { send(true) }
                    }

                    override fun onLosing(network: Network, maxMsToLive: Int) {
                        super.onLosing(network, maxMsToLive)
                        launch { send(false) }
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        launch { send(false) }
                    }

                    override fun onUnavailable() {
                        super.onUnavailable()
                        launch { send(false) }
                    }
                }
                connectivityManager.registerDefaultNetworkCallback(callback)
                awaitClose {
                    connectivityManager.unregisterNetworkCallback(callback)
                }
            }.distinctUntilChanged()
        }
    }
}
