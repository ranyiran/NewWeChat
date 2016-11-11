package cn.ran.wechat.net;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

import java.io.File;

import cn.ran.wechat.I;
import cn.ran.wechat.SuperWeChatHelper;
import cn.ran.wechat.bean.Result;
import cn.ran.wechat.utils.L;
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

    public static void searchUser(Context mContext, String userName, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME, userName)
                .targetClass(String.class)
                .execute(listener);
    }
    public static void syncUser(Context mContext, String userName, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME, userName)
                .targetClass(String.class)
                .execute(listener);
    }

    public static void addContact(Context mContext, String userName, String cuserName, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_ADD_CONTACT)
                .addParam(I.Contact.USER_NAME, userName)
                .addParam(I.Contact.CU_NAME, cuserName)
                .targetClass(String.class)
                .execute(listener);
    }

    public static void deleteContact(Context mContext, String userName, String cuserName, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_DELETE_CONTACT)
                .addParam(I.Contact.USER_NAME, userName)
                .addParam(I.Contact.CU_NAME, cuserName)
                .targetClass(String.class)
                .execute(listener);
    }

    public static void loadContact(Context mContext, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_DOWNLOAD_CONTACT_ALL_LIST)
                .addParam(I.Contact.USER_NAME, EMClient.getInstance().getCurrentUser())
                .targetClass(String.class)
                .execute(listener);
        L.e(utils.toString());
    }

    public static void createGroupAvatar(Context mContext, EMGroup emGroup, File file, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_CREATE_GROUP)
                .addParam(I.Group.HX_ID, emGroup.getGroupId())
                .addParam(I.Group.NAME, emGroup.getGroupName())
                .addParam(I.Group.DESCRIPTION, emGroup.getDescription())
                .addParam(I.Group.OWNER, emGroup.getOwner())
                .addParam(I.Group.IS_PUBLIC, String.valueOf(emGroup.isPublic()))
                .addParam(I.Group.ALLOW_INVITES, String.valueOf(emGroup.isAllowInvites()))
                .addFile2(file)
                .targetClass(String.class)
                .post()
                .execute(listener);
    }

    public static void createGroupAvatar(Context mContext, EMGroup emGroup, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_CREATE_GROUP)
                .addParam(I.Group.HX_ID, emGroup.getGroupId())
                .addParam(I.Group.NAME, emGroup.getGroupName())
                .addParam(I.Group.DESCRIPTION, emGroup.getDescription())
                .addParam(I.Group.OWNER, emGroup.getOwner())
                .addParam(I.Group.IS_PUBLIC, String.valueOf(emGroup.isPublic()))
                .addParam(I.Group.ALLOW_INVITES, String.valueOf(emGroup.isAllowInvites()))
                .targetClass(String.class)
                .post()
                .execute(listener);
    }

    public static void addGroupMembers(Context mContext, EMGroup emGroup, OkHttpUtils.OnCompleteListener<String> listener) {
        String memberArr = "";
        for (String m : emGroup.getMembers()) {
            if (!m.equals(SuperWeChatHelper.getInstance().getCurrentUserName())) {
                memberArr += m + ",";

            }
        }
        memberArr = memberArr.substring(0, memberArr.length() - 1);
        L.e("addGroupMembers", "" + memberArr);
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_ADD_GROUP_MEMBERS)
                .addParam(I.Member.GROUP_HX_ID, emGroup.getGroupId())
                .addParam(I.Member.USER_NAME, memberArr)
                .targetClass(String.class)
                .execute(listener);
    }

}




