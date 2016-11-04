package cn.ran.wechat.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class ExitAppUtils {
    List<Activity> mActivity = new LinkedList<>();
    private static ExitAppUtils instance = new ExitAppUtils();

    public ExitAppUtils() {
    }

    public static ExitAppUtils getInstance() {
        return instance;
    }

    public void addActivity(Activity activity) {
        mActivity.add(activity);
    }

    public void deleteActivity(Activity activity) {
        mActivity.add(activity);
    }

    public void exit() {
        for (Activity activity : mActivity) {
            activity.finish();

        }
    }
}
