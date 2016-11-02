/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ran.wechat.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.ran.wechat.I;
import cn.ran.wechat.R;
import cn.ran.wechat.SuperWeChatHelper;
import cn.ran.wechat.bean.Result;
import cn.ran.wechat.net.NetDao;
import cn.ran.wechat.utils.CommonUtils;
import cn.ran.wechat.utils.MD5;
import cn.ran.wechat.utils.MFGT;
import cn.ran.wechat.utils.OkHttpUtils;

/**
 * register screen
 */
public class RegisterActivity extends BaseActivity {
    @InjectView(R.id.ivBack)
    ImageView ivBack;
    @InjectView(R.id.username)
    EditText userNameEditText;
    @InjectView(R.id.usernick)
    EditText userNickEditText;
    @InjectView(R.id.password)
    EditText passwordEditText;
    @InjectView(R.id.confirm_password)
    EditText confirmPwdEditText;
    @InjectView(R.id.btnRegister)
    Button btnRegister;
    @InjectView(R.id.tvCenter)
    TextView tvCenter;

    String username;
    String pwd;
    String confirm_pwd;
    String usernick;

    RegisterActivity mContext;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_register);
        ButterKnife.inject(this);
        mContext = this;
        initView();
    }

    private void initView() {
        ivBack.setVisibility(View.VISIBLE);
        tvCenter.setVisibility(View.VISIBLE);
        tvCenter.setText(R.string.register);
    }

    public void register() {
        username = userNameEditText.getText().toString().trim();
        pwd = passwordEditText.getText().toString().trim();
        confirm_pwd = confirmPwdEditText.getText().toString().trim();
        usernick = userNickEditText.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
            userNameEditText.requestFocus();
            return;
        } else if (!username.matches("[a-zA-Z]\\w{5,15}")) {
            Toast.makeText(RegisterActivity.this, R.string.user_name + "格式不对", Toast.LENGTH_SHORT).show();
            userNameEditText.requestFocus();
        } else if (TextUtils.isEmpty(usernick)) {
            Toast.makeText(this, getResources().getString(R.string.login_txt_user_empty), Toast.LENGTH_SHORT).show();
            userNickEditText.requestFocus();
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            passwordEditText.requestFocus();
            return;
        } else if (TextUtils.isEmpty(confirm_pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Confirm_password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            confirmPwdEditText.requestFocus();
            return;
        } else if (!pwd.equals(confirm_pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Two_input_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
            pd = new ProgressDialog(this);
            pd.setMessage(getResources().getString(R.string.Is_the_registered));
            pd.show();
            registerServer();
        }
    }


    private void registerServer() {
        registerAppServer();
    }

    private void registerAppServer() {
        NetDao.registSet(mContext, username, usernick, pwd, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (result == null) {

                    pd.dismiss();
                } else {
                    if (result.isRetMsg()) {
                        registerEMServer();
                    } else {
                        if (result.getRetCode() == I.MSG_REGISTER_USERNAME_EXISTS) {
                            CommonUtils.showMsgShortToast(result.getRetCode());
                            pd.dismiss();
                        } else {
                            unRegister();
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(RegisterActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
                unRegister();
                pd.dismiss();
            }
        });
    }

    private void unRegister() {
        NetDao.unregistSet(mContext, username, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                Toast.makeText(RegisterActivity.this, "取消注册成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void registerEMServer() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    // call method in SDK
                    EMClient.getInstance().createAccount(username, MD5.getMessageDigest(pwd));
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (!RegisterActivity.this.isFinishing())
                                pd.dismiss();
                            // save current user
                            SuperWeChatHelper.getInstance().setCurrentUserName(username);
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } catch (final HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (!RegisterActivity.this.isFinishing())
                                pd.dismiss();
                            unRegister();
                            int errorCode = e.getErrorCode();
                            if (errorCode == EMError.NETWORK_ERROR) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).start();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MFGT.finish(this
        );
    }

    @OnClick({R.id.ivBack, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btnRegister:
                register();
                break;
        }
    }
}
