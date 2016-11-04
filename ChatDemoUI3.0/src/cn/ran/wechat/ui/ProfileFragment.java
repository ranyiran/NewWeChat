package cn.ran.wechat.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.ran.wechat.R;
import cn.ran.wechat.utils.MFGT;

/**
 * Created by Administrator on 2016/11/4.
 */
public class ProfileFragment extends Fragment {
    @InjectView(R.id.tvTitle)
    TextView tvTitle;
    @InjectView(R.id.ivRight)
    ImageView ivRight;
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
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        initView();
        setUserInfo();
    }

    private void initView() {
        tvTitle.setText(R.string.app_name);
        tvTitle.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.icon_add);
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

    @OnClick({R.id.ivRight, R.id.rl_item_avatar, R.id.tv_money, R.id.tv_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivRight:
                break;
            case R.id.rl_item_avatar:
                break;
            case R.id.tv_money:
                break;
            case R.id.tv_setting:
                break;
        }
    }
}
