package cn.ran.wechat.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.redpacketui.utils.RedPacketUtil;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.ran.wechat.Constant;
import cn.ran.wechat.R;
import cn.ran.wechat.utils.MFGT;

/**
 * Created by Administrator on 2016/11/4.
 */
public class ProfileFragment extends Fragment {
    @InjectView(R.id.user_head_avatar)
    ImageView userHeadAvatar;
    @InjectView(R.id.user_usernick)
    TextView userUsernick;
    @InjectView(R.id.user_usernick_number)
    TextView userUsernickNumber;
    @InjectView(R.id.rl_item_avatar)
    RelativeLayout rlItemAvatar;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.tv_setting)
    TextView tvSetting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, view);
        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            return;
        }
            setUserInfo();


    }


    private void setUserInfo() {
        EaseUserUtils.setCurrentAppUserAvatar(getActivity(), userHeadAvatar);
        EaseUserUtils.setCurrentAppUserNick(userUsernick);
        EaseUserUtils.setCurrentAppUserNameWithNo(userUsernickNumber);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.rl_item_avatar, R.id.tv_money, R.id.tv_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_item_avatar:

                break;
            case R.id.tv_money:
                //red packet code : 进入零钱页面
                RedPacketUtil.startChangeActivity(getActivity());
                break;
            //end of red packet code
            case R.id.tv_setting:
                MFGT.gotoSettingActivity(getActivity());
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (((MainActivity) getActivity()).isConflict) {
            outState.putBoolean("isConflict", true);
        } else if (((MainActivity) getActivity()).getCurrentAccountRemoved()) {
            outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
        }
    }
}
