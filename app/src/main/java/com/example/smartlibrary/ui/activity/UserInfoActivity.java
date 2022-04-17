package com.example.smartlibrary.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.smartlibrary.R;
import com.example.smartlibrary.base.BaseActivity;
import com.example.smartlibrary.bean.UseInfoBean;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.widget.ItemGroup;
import com.example.smartlibrary.widget.NormalTitleBar;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends BaseActivity  {

    private static final int EDIT_NAME = 1;
    private static final int EDIT_IDENTITY = 2;
    private static final int EDIT_AGE = 3;
    private static final int EDIT_TEL = 4;
    private static final int EDIT_SCHOOL = 5;
    private static final int EDIT_CLASS = 6;
    private UseInfoBean.UserBean userInfo;

    @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.civ_pic)
    CircleImageView pic;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.ig_name)
    ItemGroup igName;
    @BindView(R.id.ig_identity)
    ItemGroup igIdentity;
    @BindView(R.id.ig_sex)
    ItemGroup igSex;
    @BindView(R.id.ig_age)
    ItemGroup igAge;
    @BindView(R.id.ig_tel)
    ItemGroup igTel;
    @BindView(R.id.ig_school)
    ItemGroup igSchool;
    @BindView(R.id.ig_birth)
    ItemGroup igBirth;
    @BindView(R.id.ig_class)
    ItemGroup igClass;
    @BindView(R.id.ig_address)
    ItemGroup igAddress;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this,UpdateInfoActivity.class);
        LogUtils.logd("id == " + userInfo.getHeadPic());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initView() {
        userInfo = (UseInfoBean.UserBean) getIntent().getSerializableExtra("UserInfo");
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(Uri.parse("file:///storage/emulated/0/output_image.jpg")));
            LogUtils.logd("mUri == " + bitmap);
            if (bitmap != null){
                pic.setImageBitmap(bitmap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tvId.setText(userInfo.getStudyId());
        igName.getContentEdt().setText(userInfo.getName());
        igIdentity.getContentEdt().setText("");
        igSex.getContentEdt().setText(userInfo.getSex());
        igAge.getContentEdt().setText("");
        igTel.getContentEdt().setText(userInfo.getPhone());
        igSchool.getContentEdt().setText("");
        igBirth.getContentEdt().setText(userInfo.getDescb());
        igClass.getContentEdt().setText(userInfo.getClasses());
        igAddress.getContentEdt().setText(userInfo.getAddress());

        ntb.setRightTitle("保存");
        ntb.setRightTitleVisibility(true);
        ntb.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInfo();
            }
        });
        ntb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.ig_name,R.id.ig_identity,
              R.id.ig_sex,R.id.ig_age,
              R.id.ig_tel,R.id.ig_school,
              R.id.ig_birth,R.id.ig_class,
              R.id.ig_address})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.ig_name:
                intent.putExtra("updateflag","姓名");
                startActivityForResult(intent,EDIT_NAME);
                break;
            case R.id.ig_identity:
                intent.putExtra("updateflag","身份证");
                startActivityForResult(intent,EDIT_IDENTITY);
                break;
            case R.id.ig_sex:
                break;
            case R.id.ig_age:
                intent.putExtra("updateflag","年龄");
                startActivityForResult(intent,EDIT_AGE);
                break;
            case R.id.ig_tel:
                intent.putExtra("updateflag","电话号码");
                startActivityForResult(intent,EDIT_TEL);
                break;
            case R.id.ig_school:
                intent.putExtra("updateflag","学校");
                startActivityForResult(intent,EDIT_SCHOOL);
                break;
            case R.id.ig_birth:

                break;
            case R.id.ig_class:
                intent.putExtra("updateflag","班级");
                startActivityForResult(intent,EDIT_CLASS);
                break;
            case R.id.ig_address:
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == EDIT_NAME){
                igName.getContentEdt().setText(data.getStringExtra("updateinfo"));
            }
        }

    }

    private void saveInfo() {

    }
}