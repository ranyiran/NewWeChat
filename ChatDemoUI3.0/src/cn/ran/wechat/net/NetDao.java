package cn.ran.wechat.net;

import android.content.Context;

import java.io.File;

import cn.ran.wechat.I;
import cn.ran.wechat.bean.Result;
import cn.ran.wechat.utils.MD5;
import cn.ran.wechat.utils.OkHttpUtils;


/**
 * Created by Administrator on 2016/10/17.
 */
public class NetDao {


    public static void loginSet(Context mContext, String userName, String passWord, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME, userName)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(passWord))
                .targetClass(String.class)
                .execute(listener);
    }

    public static void registSet(Context mContext, String username, String usernick, String password, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.User.NICK, usernick)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(Result.class)
                .post()
                .execute(listener);
    }

    public static void unregistSet(Context mContext, String username, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME, username)
                .targetClass(Result.class)
                .execute(listener);
    }

    public static void updateNice(Context mContext, String username, String nick, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.User.NICK, nick)
                .targetClass(String.class)
                .execute(listener);
    }

    public static void updateAvatar(Context mContext, String username, File file, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID, username)
                .addParam(I.AVATAR_TYPE, I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .targetClass(String.class)
                .post()
                .execute(listener);
    }
}

