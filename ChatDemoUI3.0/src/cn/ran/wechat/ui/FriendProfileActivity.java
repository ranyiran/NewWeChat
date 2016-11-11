package cn.ran.wechat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.User;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.ran.wechat.I;
import cn.ran.wechat.R;
import cn.ran.wechat.SuperWeChatHelper;
import cn.ran.wechat.bean.Result;
import cn.ran.wechat.net.NetDao;
import cn.ran.wechat.utils.L;
import cn.ran.wechat.utils.MFGT;
import cn.ran.wechat.utils.OkHttpUtils;
import cn.ran.wechat.utils.ResultUtils;

/**
 * Created by Administrator on 2016/11/7.
 */
public class FriendProfileActivity extends BaseActivity {

    @InjectView(R.id.ivBack)
    ImageView ivBack;
    @InjectView(R.id.tvCenter)
    TextView tvCenter;
    @InjectView(R.id.user_head_avatar)
    ImageView userHeadAvatar;
    @InjectView(R.id.user_usernick)
    TextView userUsernick;
    String username = null;
    String usernick = null;
    @InjectView(R.id.user_usernick_number)
    TextView userUsernickNumber;
    User user = null;
    @InjectView(R.id.btn_send_msg)
    Button btnSendMsg;
    @InjectView(R.id.btn_add_contact)
    Button btnAddContact;
    @InjectView(R.id.btn_send_video)
    Button btnSendVideo;

    boolean isFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frient_profile);
        ButterKnife.inject(this);
        username = getIntent().getStringExtra(I.User.USER_NAME);
        // user = (User) getIntent().getSerializableExtra(I.User.USER_NAME);
        L.e("FriendProfileActivity=====" + username);
        if (username == null) {
            MFGT.finish(this);
            return;
        }
        user = SuperWeChatHelper.getInstance().getAppContactList().get(username);
       // usernick = SuperWeChatHelper.getInstance().getCurrentUser().getMUserNick();
        initView();
        if (user == null) {
            isFriend = false;
        } else {
            setUserInfo();
            isFriend = true;
        }
        isFriend(isFriend);
        syncUserInfo();
    }

    private void syncFail() {
        if (isFriend) {
            MFGT.finish(this);
            return;
        }

    }

    private void syncUserInfo() {
        NetDao.syncUser(this, username, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                L.e("syncUserInfo===" + s.toString());
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    if (result != null && result.isRetMsg()) {
                        User u = (User) result.getRetData();
                        if (u != null) {
                            if (isFriend == true) {
                                SuperWeChatHelper.getInstance().saveAppContact(u);
                            }
                            user = u;
                            setUserInfo();
                        } else {
                            syncFail();
                        }
                    } else {
                        syncFail();
                    }

                } else {
                    syncFail();
                }

            }

            @Override
            public void onError(String error) {
                syncFail();
            }
        });

    }

    private void initView() {
        ivBack.setVisibility(View.VISIBLE);
        tvCenter.setVisibility(View.VISIBLE);
        tvCenter.setText(getString(R.string.userinfo_txt_profile));

    }

    private void setUserInfo() {
        EaseUserUtils.setAppUserAvatar(this, user.getMUserName(), userHeadAvatar);
        EaseUserUtils.setAppUserNick(user.getMUserNick(), userUsernick);
        EaseUserUtils.setAppUserNameWithNo(user.getMUserName(), userUsernickNumber);
    }


    public void isFriend(boolean isFriend) {
        if (isFriend) {
            btnSendMsg.setVisibility(View.VISIBLE);
            btnSendVideo.setVisibility(View.VISIBLE);
        } else {
            btnAddContact.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.ivBack, R.id.btn_send_msg, R.id.btn_add_contact, R.id.btn_send_video})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_msg:
                MFGT.gotoChat(this, user.getMUserName());
                break;
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btn_add_contact:
                MFGT.gotoAddFriendMsg(this, user.getMUserName());
                break;

            case R.id.btn_send_video:
                if (!EMClient.getInstance().isConnected())
                    Toast.makeText(this, R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
                else {
                    startActivity(new Intent(this, VideoCallActivity.class).putExtra("username", user.getMUserName())
                            .putExtra("isComingCall", false));
                    // videoCallBtn.setEnabled(false);
                    //   inputMenu.hideExtendMenuContainer();
                }
                break;
        }
    }
}
