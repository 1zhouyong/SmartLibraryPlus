package com.example.smartlibrary.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.smartlibrary.R;
import com.example.smartlibrary.base.BaseMvpActivity;
import com.example.smartlibrary.bean.CityBean;
import com.example.smartlibrary.bean.ProvinceBean;
import com.example.smartlibrary.bean.UseInfoBean;
import com.example.smartlibrary.contract.UserInfoContract;
import com.example.smartlibrary.presenter.UserInfoPresenter;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.utils.MapToRequestBodyUtil;
import com.example.smartlibrary.utils.PublicTools;
import com.example.smartlibrary.utils.ShareUtils;
import com.example.smartlibrary.widget.ItemGroup;
import com.example.smartlibrary.widget.NormalTitleBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends BaseMvpActivity<UserInfoPresenter> implements UserInfoContract.View {

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
    private OptionsPickerView pvOptions;
    private ArrayList<String> optionsItems_gender = new ArrayList<>();
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private UserInfoPresenter presenter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this,UpdateInfoActivity.class);
        initOptionData();
        presenter = new UserInfoPresenter();
        presenter.attachView(this);
        token = ShareUtils.getString(this, "token", "");

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
        if (userInfo != null){
            tvId.setText(userInfo.getStudyId());
            igName.getContentEdt().setText(userInfo.getName());
            igSex.getContentEdt().setText(userInfo.getSex());
            igTel.getContentEdt().setText(userInfo.getPhone());
            igBirth.getContentEdt().setText(userInfo.getDescb());
            igClass.getContentEdt().setText(userInfo.getClasses());
            igAddress.getContentEdt().setText(userInfo.getAddress());
        }
        igSchool.getContentEdt().setText(ShareUtils.getString(this,"school" ));
        igAge.getContentEdt().setText(ShareUtils.getString(this,"age" ));
        igIdentity.getContentEdt().setText(ShareUtils.getString(this,"identity" ));



        ntb.setRightTitle("保存");
        ntb.setTitleText("个人信息");
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
                initSexPickerView();
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
                initBirthPickerView();
                break;
            case R.id.ig_class:
                intent.putExtra("updateflag","班级");
                startActivityForResult(intent,EDIT_CLASS);
                break;
            case R.id.ig_address:
                initAddressPickerView();
                break;


        }
    }

    // 地区选择器
    private void initAddressPickerView() {
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //选择了则显示并暂存LoginUser，退出时在保存至数据库
                String tx;
                LogUtils.logd("options1Items == " + options1Items.size()
                +",options2Items == " + options2Items.size()+",options1 == " + options1
                +",options2 == " + options2);
                if (options2Items.get(options1).size() != 0){
                    tx = options1Items.get(options1).getPickerViewText()
                            + options2Items.get(options1).get(options2);
                }else {
                    tx = options1Items.get(options1).getPickerViewText();
                }

                igAddress.getContentEdt().setText(tx);

            }
        }).setCancelColor(Color.GRAY).build();
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }




    //生日选择器
    private void initBirthPickerView() {
        //修改打开的默认时间，如果选择过则是选择过的时间，否则是系统默认时间
        Calendar selectedDate = Calendar.getInstance();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                if (userInfo== null || userInfo.getDescb().isEmpty()){
                    selectedDate.setTime(sdf.parse(new Date().toString()));
                }else {
                    selectedDate.setTime(sdf.parse(userInfo.getDescb()));
                }
            }catch (ParseException e){
                e.printStackTrace();
            }
        //初始化picker并show
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //选择了则显示并暂存LoginUser，退出时在保存至数据库
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                igBirth.getContentEdt().setText(sdf.format(date));

            }
        }).setDate(selectedDate).setCancelColor(Color.GRAY).build();
        pvTime.show();
    }

    //初始化性别、地址和生日的数据
    private void initOptionData(){
        //性别选择器数据
        optionsItems_gender.add(new String("保密"));
        optionsItems_gender.add(new String("男"));
        optionsItems_gender.add(new String("女"));

        //地址选择器数据
        String province_data = PublicTools.readJsonFile(this,"province.json");
        String city_data = PublicTools.readJsonFile(this,"city.json");

        Gson gson = new Gson();

        options1Items = gson.fromJson(province_data, new TypeToken<ArrayList<ProvinceBean>>(){}.getType());
        ArrayList<CityBean> cityBean_data = gson.fromJson(city_data, new TypeToken<ArrayList<CityBean>>(){}.getType());
        for(ProvinceBean provinceBean:options1Items){
            ArrayList<String> temp = new ArrayList<>();
            for (CityBean cityBean : cityBean_data){
                if(provinceBean.getProvince().equals(cityBean.getProvince())){
                    temp.add(cityBean.getName());
                }
            }
            options2Items.add(temp);
        }

    }


    //性别选择器
    private void initSexPickerView() {
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //选择了则显示并暂存LoginUser，退出时在保存至数据库
                String tx = optionsItems_gender.get(options1);
                igSex.getContentEdt().setText(tx);
                LogUtils.logd("tx ===" + tx);

            }
        }).setCancelColor(Color.GRAY).build();
        pvOptions.setPicker(optionsItems_gender);
        pvOptions.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == EDIT_NAME){
                igName.getContentEdt().setText(data.getStringExtra("updateinfo"));
            }else if (requestCode == EDIT_IDENTITY){
                igIdentity.getContentEdt().setText(data.getStringExtra("updateinfo"));
            }else if (requestCode == EDIT_AGE){
                igAge.getContentEdt().setText(data.getStringExtra("updateinfo"));
            }else if (requestCode == EDIT_CLASS){
                igClass.getContentEdt().setText(data.getStringExtra("updateinfo"));
            }else if (requestCode == EDIT_SCHOOL){
                igSchool.getContentEdt().setText(data.getStringExtra("updateinfo"));
            }else if (requestCode == EDIT_TEL){
                igTel.getContentEdt().setText(data.getStringExtra("updateinfo"));
            }

        }

    }

    private void saveInfo() {
        Map<String,String> map = new HashMap<>();
        map.put("address",
                igAddress.getContentEdt().getText().toString().isEmpty()?userInfo.getAddress():igAddress.getContentEdt().getText().toString());
        map.put("classes",
                igClass.getContentEdt().getText().toString().isEmpty()?userInfo.getClasses():igClass.getContentEdt().getText().toString());
        map.put("descb",
                igBirth.getContentEdt().getText().toString().isEmpty()?userInfo.getDescb():igBirth.getContentEdt().getText().toString());
        map.put("id", userInfo.getId()+"");
        map.put("name",
                igName.getContentEdt().getText().toString().isEmpty()?userInfo.getName():igName.getContentEdt().getText().toString());
        map.put("operator",userInfo.getStudyId());
        map.put("phone",
                igTel.getContentEdt().getText().toString().isEmpty()?userInfo.getPhone():igTel.getContentEdt().getText().toString());
        map.put("sex",
                igSex.getContentEdt().getText().toString().isEmpty()?userInfo.getSex():igSex.getContentEdt().getText().toString());





        presenter.updateInfo(token, MapToRequestBodyUtil.convertMapToBody(map));

    }

    @Override
    public void updateInfoSuccess(boolean isSuccess) {
        if (isSuccess){
            showLongToast("修改成功");
            ShareUtils.putString(this,"school",igSchool.getContentEdt().getText().toString());
            ShareUtils.putString(this,"age",igAge.getContentEdt().getText().toString());
            ShareUtils.putString(this,"identity",igIdentity.getContentEdt().getText().toString());

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
}