package com.example.smartlibrary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.smartlibrary.R;
import com.example.smartlibrary.base.BaseActivity;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.utils.PublicTools;
import com.example.smartlibrary.widget.NormalTitleBar;

import butterknife.BindView;

public class UpdateInfoActivity extends BaseActivity implements View.OnClickListener {

    private Intent intent;
    private String updateflag;
    @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.edit_text)
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_info;
    }

    @Override
    public void initView() {
        intent = getIntent();
        updateflag = intent.getStringExtra("updateflag");
        ntb.setTitleText("编辑"+ updateflag);
        if (updateflag .equals("身份证") ||updateflag .equals("年龄")){
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else if (updateflag .equals("电话号码")){
            editText.setInputType(InputType.TYPE_CLASS_PHONE);
        }
        ntb.setOnBackListener(this);
    }

    @Override
    public void onClick(View v) {
        String updateInfo = editText.getText().toString().trim();
        if (updateInfo.isEmpty()){
            showLongToast("输入内容不得为空");
        }else {
            if (!(updateflag.equals("身份证") && PublicTools.judgeId(updateInfo))){
                showLongToast("身份证格式不正确");
                return;
            }

            if (!(updateflag.equals("年龄") && Integer.valueOf(updateInfo) < 0 || Integer.valueOf(updateInfo) > 120)){
                showLongToast("年龄格式不正确");
                return;
            }

            if (!(updateflag.equals("电话号码") && PublicTools.validatePhoneNumber(updateInfo))){
                showLongToast("电话号码格式不正确");
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("updateinfo",updateInfo);
            setResult(RESULT_OK,intent);
            finish();
        }
    }



}