package cn.ran.wechat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.ran.wechat.R;
import cn.ran.wechat.utils.MFGT;

public class GuideActivity extends BaseActivity {

    @InjectView(R.id.guideLogin)
    Button guideLogin;
    @InjectView(R.id.guideRegister)
    Button guideRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.guideLogin, R.id.guideRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guideLogin:
                MFGT.gotoLogin(this);
                break;
            case R.id.guideRegister:
                MFGT.gotoRegister(this);
                break;
        }
    }
}
