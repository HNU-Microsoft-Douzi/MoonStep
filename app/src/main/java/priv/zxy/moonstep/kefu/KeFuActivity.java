package priv.zxy.moonstep.kefu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.adapter.ChattingMessageAdapter;
import priv.zxy.moonstep.framework.message.Message;
import priv.zxy.moonstep.data.bean.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class KeFuActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private List<Message> lists = new ArrayList<Message>();
    private ChattingMessageAdapter mAdapter;
    private Button back;
    private Button person_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fg_main_fifth_subpage);
    }

    @Override
    protected void initView(){
        back = this.findViewById(R.id.back);
        person_info = this.findViewById(R.id.person_info);

        recyclerView = this.findViewById(R.id.recyclerview);
        mAdapter = new ChattingMessageAdapter(getApplicationContext());
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData(){
        mAdapter.addAll(lists);
        // 设置列表布局管理
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        recyclerView.setAdapter(mAdapter);

        // 对两个按钮设立监听事件
        back.setOnClickListener(this);
        person_info.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(back)) {
            // 这里直接调用了返回按键，没有对数据进行保存，设立客服系统的时候，要增加一个函数，对当前activity的状态进行保存
            savedThisState();
            FinishesThisActivity();
        }
        if (v.equals(person_info)) {
            jumpToPersonInfoPage();
        }
    }

    public void FinishesThisActivity(){
        this.finish();
    }
    /**
     * 这里调用返回键以后，保存客服对话信息的数据在这个函数里面
     */
    public void savedThisState(){

    }

    /**
     * 这里跳转到对方的个人信息栏中
     * （暂时还未写好）
     */
    public void jumpToPersonInfoPage(){

    }
}
