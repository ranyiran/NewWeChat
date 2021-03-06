/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p/>
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

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.ran.wechat.R;
import cn.ran.wechat.adapter.NewFriendsMsgAdapter;
import cn.ran.wechat.db.InviteMessgeDao;
import cn.ran.wechat.domain.InviteMessage;
import cn.ran.wechat.utils.MFGT;

/**
 * Application and notification
 */
public class NewFriendsMsgActivity extends BaseActivity {

    @InjectView(R.id.ivBack)
    ImageView ivBack;
    @InjectView(R.id.tvCenter)
    TextView tvCenter;
    @InjectView(R.id.list)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_new_friends_msg);
        ButterKnife.inject(this);
        ivBack.setVisibility(View.VISIBLE);
        tvCenter.setVisibility(View.VISIBLE);
        tvCenter.setText(getString(R.string.recommended_friends));

        String userid = getIntent().getStringExtra("userid");
        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();
        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);

    }

    public void back(View view) {
        finish();
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }
}
