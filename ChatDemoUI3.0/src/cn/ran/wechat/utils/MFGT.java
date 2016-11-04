package cn.ran.wechat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cn.ran.wechat.R;
import cn.ran.wechat.ui.GuideActivity;
import cn.ran.wechat.ui.LoginActivity;
import cn.ran.wechat.ui.MainActivity;
import cn.ran.wechat.ui.RegisterActivity;
import cn.ran.wechat.ui.SettingsActivity;


public class MFGT {
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void gotoMainActivity(Activity context) {
        startActivity(context, MainActivity.class);
    }

    public static void backtoMainActivity(Activity context) {
        startActivity(context, MainActivity.class);
    }

    public static void startActivity(Activity context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    //    intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId);


    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoLogin(Activity mContext) {
        startActivity(mContext, LoginActivity.class);

    }

    public static void gotoRegister(Activity mContext) {
        startActivity(mContext, RegisterActivity.class);

    }

    public static void gotoGuidActivity(Activity mContext) {
        startActivity(mContext, GuideActivity.class);
        mContext.overridePendingTransition(R.anim.push_bottom_in, R.anim.push_top_out);
    }
    public static void gotoSettingActivity(Activity mContext) {
        startActivity(mContext, SettingsActivity.class);
        mContext.overridePendingTransition(R.anim.push_bottom_in, R.anim.push_top_out);
    }

}
