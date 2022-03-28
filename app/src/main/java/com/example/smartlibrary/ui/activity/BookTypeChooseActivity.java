package com.example.smartlibrary.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlibrary.R;
import com.example.smartlibrary.adapter.channel.ChannelAdapter;
import com.example.smartlibrary.adapter.channel.ChannelBean;
import com.example.smartlibrary.adapter.channel.ToucheCallBcak;
import com.example.smartlibrary.base.BaseActivity;
import com.example.smartlibrary.utils.LogUtils;
import com.example.smartlibrary.utils.ShareUtils;

import java.util.List;

import butterknife.BindView;

public class BookTypeChooseActivity extends BaseActivity implements ChannelAdapter.OnStartDragListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.channl_recyler)
    RecyclerView channelRecyler;
    private ChannelAdapter channelAdapter;
    private GridLayoutManager manager;
    private ItemTouchHelper mTouchHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecy();

    }

    private void initRecy() {
        channelAdapter = new ChannelAdapter(this, getDatas(), channelRecyler);
        manager = new GridLayoutManager(this,4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = channelAdapter.getItemViewType(position);
                if(itemViewType == channelAdapter  .CHANNEL_TITLE){
                    return 4;
                }
                return 1;
            }
        });
        channelRecyler.setLayoutManager(manager);
        channelRecyler.setAdapter(channelAdapter);
        //创建ItemTouchHelper
        mTouchHelper = new ItemTouchHelper(new ToucheCallBcak(channelAdapter));
        //attach到RecyclerView中
        mTouchHelper.attachToRecyclerView(channelRecyler);
        channelAdapter.setOnStartDragListener(this);
    }

    private List<ChannelBean> getDatas() {
        Bundle bundle = getIntent().getExtras();
        List<ChannelBean> bookTypeChannelList = (List<ChannelBean>) bundle.getSerializable("BookTypeChannelList");

        LogUtils.logd("bookTypeChannelList == " + bookTypeChannelList);

        return bookTypeChannelList;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_type_choose;
    }

    @Override
    public void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (channelAdapter.getmMyChannel().size() == 0){
                    showLongToast("我的频道不能为空");
                }else {
                    ShareUtils.putList(BookTypeChooseActivity.this,"MyChannel",channelAdapter.getmMyChannel());
                    ShareUtils.putList(BookTypeChooseActivity.this,"OthersChannel",channelAdapter.getmOtherChannel());
                    setResult(RESULT_OK);
                    finish();
                }



                for (int i = 0; i < channelAdapter.getmMyChannel().size(); i++) {
                    System.out.println("MyChannel === " + channelAdapter.getmMyChannel().get(i).getSrc());
                }
                for (int i = 0; i < channelAdapter.getmOtherChannel().size(); i++) {
                    System.out.println("OtherChannel === " + channelAdapter.getmOtherChannel().get(i).getSrc());
                }
            }
        });
    }

    @Override
    public void startDrag(RecyclerView.ViewHolder holder) {
        mTouchHelper.startDrag(holder);
    }
}