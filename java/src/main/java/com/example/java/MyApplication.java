package com.example.java;

import android.app.Application;
import android.util.Log;

import com.walletconnect.android.Core;
import com.walletconnect.android.CoreClient;
import com.walletconnect.android.relay.ConnectionType;
import com.walletconnect.sign.client.Sign;
import com.walletconnect.sign.client.SignClient;

import java.util.Collections;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Author: clement
 * Create: 2022/11/29
 * Desc:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        long time1 = System.currentTimeMillis();
        initCoreClient();

        long time2 = System.currentTimeMillis();
        Log.e("initCore", String.valueOf(time2 - time1));

        initSignClient();
        long time3 = System.currentTimeMillis();
        Log.e("initSign", String.valueOf(time3 - time2));

    }

    private void initCoreClient() {
        String relayUrl = "relay.walletconnect.com";
        String projectId = "53db0f6deb8069ef44047f4deedc90f5";
        String serverUrl = "wss://" + relayUrl + "?projectId=" + projectId;
        String redirect = "my-wallet-wc://request";
        Core.Model.AppMetaData metaData = new Core.Model.AppMetaData("JavaTest", "desc", "",
                Collections.singletonList(""), redirect);
        CoreClient.INSTANCE.initialize(metaData, serverUrl, ConnectionType.AUTOMATIC, this, null);
    }

    private void initSignClient() {
        Sign.Params.Init init = new Sign.Params.Init(CoreClient.INSTANCE);
        SignClient.INSTANCE.initialize(init, new Function1<Sign.Model.Error, Unit>() {
            @Override
            public Unit invoke(Sign.Model.Error error) {
                return null;
            }
        });
    }
}
