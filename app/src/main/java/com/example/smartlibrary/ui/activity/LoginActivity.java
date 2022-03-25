package com.example.smartlibrary.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartlibrary.R;
import com.example.smartlibrary.base.BaseMvpActivity;
import com.example.smartlibrary.bean.BaseObjectBean;
import com.example.smartlibrary.contract.LoginContract;
import com.example.smartlibrary.presenter.LoginPresenter;
import com.example.smartlibrary.ui.view.OwlView;
import com.example.smartlibrary.utils.ShareUtils;

import butterknife.BindView;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {

    @BindView(R.id.email)
    EditText edtEmail;
    @BindView(R.id.password)
    EditText edtPassword;
    @BindView(R.id.cb_remember_password)
    CheckBox cdRememberPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.owl_view)
    OwlView ownView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    public void initView() {
        initOwnView();
        initData();
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
        btnLogin.setOnClickListener(this);
    }

    private void initData() {
        edtEmail.setText(ShareUtils.getString(this,"username"));
        edtPassword.setText(ShareUtils.getString(this,"password"));
        cdRememberPassword.setChecked(ShareUtils.getBoolean(this,"remember",true));
    }

    private void initOwnView() {
        edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ownView.open();

                } else {
                    ownView.close();
                }
            }
        });
    }

    @Override
    public void showLoading() {
        startProgressDialog("正在登录...");
    }

    @Override
    public void hideLoading() {
        stopProgressDialog();
    }

    @Override
    public void onError(String errMessage) {

    }

    @Override
    public void onSuccess(BaseObjectBean<String>  bean) {
        Log.d("LoginActivity", "onSuccess: "+bean.getResult());
        ShareUtils.putString(this,"token", bean.getResult());
        startActivity(MainActivity.class,null);
    }


    @Override
    public void onClick(View view) {
        if (getUsername().isEmpty() || getEdtPassword().isEmpty()) {
            Toast.makeText(this, "帐号密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.login(getUsername(), getEdtPassword());
        if (cdRememberPassword.isChecked()){
            saveUsernameAndPassword(getUsername(),getEdtPassword(),cdRememberPassword.isChecked());
        }else {
            saveUsernameAndPassword("","",cdRememberPassword.isChecked());
        }
    }

    private void saveUsernameAndPassword(String username, String edtPassword, boolean checked) {
        ShareUtils.putString(this,"username",username);
        ShareUtils.putString(this,"password",edtPassword);
        ShareUtils.putBoolean(this,"remember",checked);
    }


    /**
     * @return 帐号
     */
    private String getUsername() {
        return edtEmail.getText().toString().trim();
    }

    /**
     * @return 密码
     */
    private String getEdtPassword() {
        return edtPassword.getText().toString().trim();
    }
}