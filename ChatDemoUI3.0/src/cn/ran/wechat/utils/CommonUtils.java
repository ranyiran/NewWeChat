package cn.ran.wechat.utils;

import android.widget.Toast;

import cn.ran.wechat.I;
import cn.ran.wechat.R;
import cn.ran.wechat.SuperWeChatApplication;


public class CommonUtils {
    public static void showLongToast(String msg) {
        Toast.makeText(SuperWeChatApplication.applicationContext, msg, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(String msg) {
        Toast.makeText(SuperWeChatApplication.applicationContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(int rId) {
        showLongToast(SuperWeChatApplication.applicationContext.getString(rId));
    }

    public static void showShortToast(int rId) {
        showShortToast(SuperWeChatApplication.applicationContext.getString(rId));
    }

    public static void showMsgShortToast(int msgId) {
        String msg = String.valueOf(msgId);
        if (msgId > 0) {
            showShortToast(SuperWeChatApplication.getInstance().getResources()
                    .getIdentifier(I.MSG_PREFIX_MSG + msg, "string",
                            SuperWeChatApplication.getInstance().getPackageName()) );
            L.i("fanhuizhi");
        } else {
            showShortToast(R.string.Msg_1);
        }
    }
}
