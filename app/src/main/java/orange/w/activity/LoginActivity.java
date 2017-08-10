package orange.w.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import orange.w.R;
import orange.w.databinding.ActivityLoginBinding;

public class LoginActivity extends Activity implements View.OnClickListener {
    private ActivityLoginBinding mBinding;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
        doLogin();
    }

    private void doLogin() {
        String[] arr = {"测试标签1", "测试标签2", "测试标签3", "测试标签4", "测试标签5", "测试标签6", "测试标签7"};
        List<String> list = new ArrayList<>();
        list.add("测试标签1");
        list.add("测试标签2");
        list.add("测试标签3");
        list.add("测试标签4");
        list.add("测试标签4");
    }

    private void initView() {
        mBinding.tvLogin.setOnClickListener(this);
        mBinding.tvLosePass.setOnClickListener(this);
        mBinding.usernameWrapper.setHint("Username");
        mBinding.passwordWrapper.setHint("Password");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_losePass:
                startActivity(new Intent(this, GDMapActivity.class));
                break;

        }
    }
}
