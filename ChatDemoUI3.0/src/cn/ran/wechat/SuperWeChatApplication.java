/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ran.wechat;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.easemob.redpacketsdk.RedPacket;

public class SuperWeChatApplication extends Application {

    public static Context applicationContext;
    private static SuperWeChatApplication instance;
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
     */
    public static String currentUserNick = "";


    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        applicationContext = this;
        instance = this;

        //init demo helper
        SuperWeChatHelper.getInstance().init(applicationContext);
        //red packet code : 初始化红包上下文，开启日志输出开关
        RedPacket.getInstance().initContext(applicationContext);
        RedPacket.getInstance().setDebugMode(true);
        //end of red packet code
    }

    public static SuperWeChatApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
