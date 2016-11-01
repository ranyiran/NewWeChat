package cn.ran.wechat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.ran.wechat.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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
                Intent loginIntent = new Intent();
                loginIntent.setClass(this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.guideRegister:
                Intent registerIntent = new Intent();
                registerIntent.setClass(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }
    }
}
