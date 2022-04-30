package com.example.smartlibrary.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.example.smartlibrary.R;
import com.example.smartlibrary.app.BaseApplication;
import com.example.smartlibrary.base.BaseMvpFragment;
import com.example.smartlibrary.contract.MyMainContract;
import com.example.smartlibrary.presenter.MyMainPresenter;
import com.example.smartlibrary.ui.activity.MainActivity;
import com.example.smartlibrary.ui.activity.MyLectureActivity;
import com.example.smartlibrary.ui.activity.MySeatInfoActivity;
import com.example.smartlibrary.ui.activity.UpdatePwdActivity;
import com.example.smartlibrary.ui.activity.UserInfoActivity;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.utils.ShareUtils;
import com.example.smartlibrary.widget.WaveView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2011-2022, by your Signway, All rights reserved.
 * -----------------------------------------------------------------
 *
 * ProjectName: SmartLibrary
 *
 * Author: ZY
 *
 * Email: yong.zhou@signway.cn
 *
 * Description:
 *
 * -----------------------------------------------------------------
 * 2022/3/23 : Create MyMainFragment.java
 * -----------------------------------------------------------------
 */
public class MyMainFragment extends BaseMvpFragment<MyMainPresenter> implements MyMainContract.View {

    @BindView(R.id.wave_view)
    WaveView waveView;
    @BindView(R.id.img_logo)
    CircleImageView imgLogo;
    @BindView(R.id.ll_user_info)
    LinearLayout llUserInfo;
    @BindView(R.id.ll_seat_info)
    LinearLayout llSeatInfo;
    @BindView(R.id.ll_book_info)
    LinearLayout llBookInfo;
    @BindView(R.id.ll_lecture_info)
    LinearLayout llLectureInfo;
    @BindView(R.id.ll_update_pwd)
    LinearLayout llUpdatePwd;

    //打开相册的请求码
    private static final int MY_ADD_CASE_CALL_PHONE2 = 7;

    //调取系统摄像头的请求码
    private static final int MY_ADD_CASE_CALL_PHONE = 6;
    private MyMainPresenter presenter;
    private Uri mUri;
    private List<MultipartBody.Part> parts;
    private MainActivity activity;

    @Override
    protected void initView(View view) {
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2, -2);
        lp.gravity = Gravity.CENTER;
        waveView.setOnWaveAnimationListener(new WaveView.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(0, 0, 0, (int) y + 2);
                imgLogo.setLayoutParams(lp);
            }
        });
        activity = (MainActivity) getActivity();
//        ImageLoader.with(getActivity())
//                .url("file:///storage/emulated/0/output_image.jpg")
//                .placeHolder(R.mipmap.ic_launcher_round)
//                .error(R.mipmap.ic_launcher_round)
//                .scale(ScaleMode.FIT_CENTER)
//                .asCircle()
//                .into(imgLogo);

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
                    .openInputStream(Uri.parse("file:///storage/emulated/0/output_image.jpg")));
            LogUtils.logd("mUri == " + bitmap);
            if (bitmap != null){
                imgLogo.setImageBitmap(bitmap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        presenter = new MyMainPresenter();
        presenter.attachView(this);


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @OnClick({R.id.img_logo,R.id.ll_user_info,R.id.ll_seat_info,R.id.ll_book_info,R.id.ll_lecture_info,R.id.ll_update_pwd})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.img_logo:
                showImgChooseDialog();
                break;
            case R.id.ll_user_info:
                Bundle userBundle = new Bundle();
                userBundle.putSerializable("UserInfo",activity.infoBean);
                startActivity(UserInfoActivity.class, userBundle );
                break;
            case R.id.ll_seat_info:
                Bundle seatBundle = new Bundle();
                seatBundle.putString("name",activity.infoBean.getName());
                seatBundle.putString("studyId",activity.infoBean.getStudyId());
                seatBundle.putString("userId", activity.infoBean.getId()+"");
                startActivity(MySeatInfoActivity.class, seatBundle );
                break;
            case R.id.ll_book_info:

                break;
            case R.id.ll_lecture_info:
                Bundle lectureBundle = new Bundle();
                lectureBundle.putString("userId",activity.infoBean.getId()+"");
                startActivity(MyLectureActivity.class,lectureBundle);
                break;
            case R.id.ll_update_pwd:
                Bundle updateBundle = new Bundle();
//                updateBundle.putString("userId",activity.infoBean.getId()+"");
                updateBundle.putString("userName",activity.infoBean.getStudyId());
                startActivity(UpdatePwdActivity.class,updateBundle);
                break;

        }
    }

    // 拍照
    private void takePhone() {
        // 要保存的文件名
        String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
        String fileName = "photo_" + time;
        // 创建一个文件夹
        String path = Environment.getExternalStorageDirectory() + "/take_photo";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 要保存的图片文件
        File imgFile = new File(file, fileName + ".jpeg");
        // 将file转换成uri
        // 注意7.0及以上与之前获取的uri不一样了，返回的是provider路径
        mUri = getUriForFile(getActivity(), imgFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 添加Uri读取权限
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        // 或者
//        grantUriPermission("com.rain.takephotodemo", mUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 添加图片保存位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        startActivityForResult(intent, 2);
    }
    // 从file中获取uri
    // 7.0及以上使用的uri是contentProvider content://com.rain.takephotodemo.FileProvider/images/photo_20180824173621.jpg
    // 6.0使用的uri为file:///storage/emulated/0/take_photo/photo_20180824171132.jpg
    private static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.rain.takephotodemo.FileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }




    private void showImgChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//创建对话框
        View layout = getLayoutInflater().inflate(R.layout.dialog_select_photo, null);//获取自定义布局
        builder.setView(layout);//设置对话框的布局
        Dialog dialog = builder.create();//生成最终的对话框
        dialog.show();//显示对话框

        TextView takePhotoTV = layout.findViewById(R.id.photograph);
        TextView choosePhotoTV = layout.findViewById(R.id.photo);
        TextView cancelTV = layout.findViewById(R.id.cancel);
        //设置监听
        takePhotoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               takePhone();
            }
        });


        choosePhotoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(Environment.getExternalStorageDirectory(),
                        "output_image.jpg");
                mUri = Uri.fromFile(outputImage);

                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                //此处调用了图片选择器
                //如果直接写intent.setDataAndType("image/*");
                //调用的是系统图库
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                startActivityForResult(intent, 2);
                dialog.dismiss();
            }
        });
        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }



    /**
     * startActivityForResult执行后的回调方法，接收返回的图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            //此处启动裁剪程序
            Intent intent = new Intent("com.android.camera.action.CROP");
            //此处注释掉的部分是针对android 4.4路径修改的一个测试
            //有兴趣的读者可以自己调试看看
            intent.setDataAndType(data.getData(), "image/*");
            intent.putExtra("scale", true);
            //裁减比例1：1
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //裁剪后图片大小
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
            startActivityForResult(intent, 3);
        }

        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            try {
                //将output_image.jpg对象解析成Bitmap对象，然后设置到ImageView中显示出来
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
                        .openInputStream(mUri));
                LogUtils.logd("mUri == " + mUri);
                imgLogo.setImageBitmap(bitmap);
                String crop = saveImage("crop", bitmap);
                if (crop != null) {
                    File file = new File(crop);
                    LogUtils.logd("bitmap == "+ file);
                    if (file.exists()) {
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);//表单类型
                        MultipartBody.Builder builder = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM);//表单类型
                        builder.addFormDataPart("headPic", file.getName(), requestFile);
                        parts = builder.build().parts();
                    }
                }
                String token = ShareUtils.getString(BaseApplication.getAppContext(), "token", "");
                presenter.uploadPic(token, "1", parts);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //保存图片
    private String saveImage(String crop, Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = crop + ".jpeg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onSuccess(String msg) {

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


