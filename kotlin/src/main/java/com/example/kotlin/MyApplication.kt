package com.example.kotlin

import android.app.Application
import android.util.Log
import com.walletconnect.android.Core
import com.walletconnect.android.CoreClient
import com.walletconnect.android.relay.ConnectionType
import com.walletconnect.sign.client.Sign
import com.walletconnect.sign.client.SignClient

/**
 *  Author: clement
 *  Create: 2022/12/1
 *  Desc:
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val time1 = System.currentTimeMillis()
        initCoreClient()

        val time2 = System.currentTimeMillis()
        Log.e("initCore", (time2 - time1).toString())

        initSignClient()
        val time3 = System.currentTimeMillis()
        Log.e("initSign", (time3 - time2).toString())

    }

    private fun initCoreClient() {
        val relayUrl = "relay.walletconnect.com"
        val projectId = "53db0f6deb8069ef44047f4deedc90f5"
        val serverUrl = "wss://$relayUrl?projectId=$projectId"
        CoreClient.initialize(
            relayServerUrl = serverUrl,
            connectionType = ConnectionType.AUTOMATIC,
            application = this,
            metaData = Core.Model.AppMetaData(
                name = "Kotlin Wallet",
                description = "Wallet description",
                url = "example.wallet",
                icons = listOf("https://gblobscdn.gitbook.com/spaces%2F-LJJeCjcLrr53DcT1Ml7%2Favatar.png?alt=media"),
                redirect = "kotlin-wallet-wc:/request",
            )
        )
    }

    private fun initSignClient() {
        val initParams = Sign.Params.Init(core = CoreClient)
        SignClient.initialize(initParams) { error ->
            Log.e("error", error.throwable.stackTraceToString())
        }
    }
}