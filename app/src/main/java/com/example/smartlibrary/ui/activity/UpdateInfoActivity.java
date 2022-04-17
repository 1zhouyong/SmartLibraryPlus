package com.example.smartlibrary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.smartlibrary.R;
import com.example.smartlibrary.base.BaseActivity;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.widget.NormalTitleBar;

import butterknife.BindView;

public class UpdateInfoActivity extends BaseActivity {

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
        ntb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("updateinfo",editText.getText().toString().trim());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}