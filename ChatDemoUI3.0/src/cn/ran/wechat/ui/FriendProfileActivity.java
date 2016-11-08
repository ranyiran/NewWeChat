package cn.ran.wechat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.domain.User;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.ran.wechat.I;
import cn.ran.wechat.R;
import cn.ran.wechat.SuperWeChatHelper;
import cn.ran.wechat.net.NetDao;
import cn.ran.wechat.utils.L;
import cn.ran.wechat.utils.MFGT;

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
    @InjectView(R.id.user_usernick_number)
    TextView userUsernickNumber;
    User user = null;
    @InjectView(R.id.btn_send_msg)
    Button btnSendMsg;
    @InjectView(R.id.btn_add_contact)
    Button btnAddContact;
    @InjectView(R.id.btn_send_video)
    Button btnSendVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frient_profile);
        ButterKnife.inject(this);
        user = (User) getIntent().getSerializableExtra(I.User.USER_NAME);
        //   L.e("FriendProfileActivity=====" + user.toString());
        if (user == null) {
            MFGT.finish(this);
        }
        initView();
    }

    private void initView() {
        ivBack.setVisibility(View.VISIBLE);
        tvCenter.setVisibility(View.VISIBLE);
        tvCenter.setText(getString(R.string.userinfo_txt_profile));
        setUserInfo();
        isFriend();

    }

    private void setUserInfo() {
        EaseUserUtils.setAppUserAvatar(this, user.getMUserName(), userHeadAvatar);
        EaseUserUtils.setAppUserNick(user.getMUserNick(), userUsernick);
        EaseUserUtils.setAppUserNameWithNo(user.getMUserName(), userUsernickNumber);
    }


    public void isFriend() {
        if (SuperWeChatHelper.getInstance().getAppContactList().containsKey(user.getMUserName())) {
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
                break;
        }
    }
}
