package com.example.smartlibrary.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smartlibrary.R;
import com.example.smartlibrary.app.BaseApplication;
import com.example.smartlibrary.base.BaseActivity;
import com.example.smartlibrary.base.BaseMvpActivity;
import com.example.smartlibrary.base.BasePresenter;
import com.example.smartlibrary.contract.UpdatePwdContract;
import com.example.smartlibrary.presenter.UpdatePwdPresenter;
import com.example.smartlibrary.utils.MapToRequestBodyUtil;
import com.example.smartlibrary.utils.PasswordValidUtil;
import com.example.smartlibrary.utils.ShareUtils;
import com.example.smartlibrary.widget.NormalTitleBar;

import java.util.HashMap;

import butterknife.BindView;

public class UpdatePwdActivity extends BaseMvpActivity<UpdatePwdPresenter> implements UpdatePwdContract.View, View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.tv_username)
    TextView tvUserName;
    @BindView(R.id.edt_newPassword)
    EditText edtNewPwd;
    @BindView(R.id.edt_confirmPassword)
    EditText edtConfirmPwd;
    @BindView(R.id.cb_showPassword)
    CheckBox cbShowPassword;
    @BindView(R.id.btn_change)
    Button btnUpdate;
    private String token;
    private UpdatePwdPresenter presenter;
    private String userName;
    private String newPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_pwd;
    }

    @Override
    public void initView() {
        userName = getIntent().getStringExtra("userName");
        tvUserName.setText(userName);
        token = ShareUtils.getString(BaseApplication.getAppContext(), "token", "");
        btnUpdate.setOnClickListener(this);
        presenter = new UpdatePwdPresenter();
        presenter.attachView(this);
        cbShowPassword.setOnCheckedChangeListener(this);
    }

    @Override
    public void updatePwdSuccess(boolean isSuccess) {
        if (isSuccess){
            showLongToast("修改密码成功");
            ShareUtils.putString(this,"password",newPwd);
        }
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
    public void onClick(View v) {
        newPwd = edtNewPwd.getText().toString().trim();
        String confirmPwd = edtConfirmPwd.getText().toString().trim();
        if (newPwd.isEmpty()){
            showShortToast("新密码不得为空");
        }else if (confirmPwd.isEmpty()){
            showShortToast("确认密码不得为空");
        }else {
            if (PasswordValidUtil.valid4(newPwd)){
                showShortToast("密码不符合规范");
            }else {
                if (!newPwd.equals(confirmPwd)){
                    showShortToast("确认密码不一致");
                }else {
                    HashMap map = new HashMap<>();
                    map.put("newPwd", newPwd);
                    map.put("studyId", userName);
                    presenter.updateNewPwd(token, MapToRequestBodyUtil.convertMapToBody(map));
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (cbShowPassword.isChecked()){
            edtNewPwd.setInputType(InputType.TYPE_CLASS_TEXT);
            edtConfirmPwd.setInputType(InputType.TYPE_CLASS_TEXT);

        }else {
            edtNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            edtConfirmPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}