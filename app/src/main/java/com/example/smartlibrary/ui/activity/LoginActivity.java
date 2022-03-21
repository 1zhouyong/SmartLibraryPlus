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

import butterknife.BindView;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {

    @BindView(R.id.email)
    EditText edtEmail;
    @BindView(R.id.password)
    EditText password;
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
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
        btnLogin.setOnClickListener(this);
    }

    private void initOwnView() {
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String errMessage) {

    }

    @Override
    public void onSuccess(BaseObjectBean<String> bean) {
        Log.d("LoginActivity", "onSuccess: "+bean.getResult());

    }


    @Override
    public void onClick(View view) {
        if (getUsername().isEmpty() || getPassword().isEmpty()) {
            Toast.makeText(this, "帐号密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.login(getUsername(), getPassword());
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
    private String getPassword() {
        return password.getText().toString().trim();
    }
}