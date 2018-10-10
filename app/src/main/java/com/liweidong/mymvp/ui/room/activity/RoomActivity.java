package com.liweidong.mymvp.ui.room.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liweidong.mymvp.R;
import com.liweidong.mymvp.base.BaseMvpActivity;
import com.liweidong.mymvp.base.contract.room.RoomContract;
import com.liweidong.mymvp.model.bean.PhoneBean;
import com.liweidong.mymvp.model.db.PhoneDatabase;
import com.liweidong.mymvp.presenter.room.RoomPresenter;
import com.liweidong.mymvp.ui.room.adapter.PhoneListAdapter;
import com.liweidong.mymvp.ui.room.fragment.EditDialog;
import com.liweidong.mymvp.widget.EasyRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:李炜东
 * Date:2018/9/11.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class RoomActivity extends BaseMvpActivity<RoomPresenter> implements RoomContract.View {

    @BindView(R.id.insert_name_edit)
    EditText insertNameEdit;
    @BindView(R.id.insert_phone_edit)
    EditText insertPhoneEdit;

    @BindView(R.id.insert_button)
    Button insertButton;
    @BindView(R.id.query_button)
    Button queryButton;

    PhoneListAdapter mAdapter;

    List<PhoneBean> list = new ArrayList<>();
    @BindView(R.id.data_list_view)
    EasyRecyclerView dataListView;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_room;
    }

    @Override
    protected void initData() {
        super.initData();

        mAdapter = new PhoneListAdapter(list, this);
        dataListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PhoneListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(PhoneBean phoneBean) {
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("PhoneBean", phoneBean);
                EditDialog mDialog = new EditDialog();
                mDialog.show(RoomActivity.this, mBundle, "edit");
                mDialog.setOnRefreshDataListener(new EditDialog.OnRefreshDataListener() {
                    @Override
                    public void onRefresh() {
                        basePresenter.getAllPhone(getApplicationContext());
                    }
                });
            }
        });

    }

    @OnClick({R.id.insert_button, R.id.query_button})
    public void onClick(View v) {
        String mName = insertNameEdit.getText().toString();
        String mPhone = insertPhoneEdit.getText().toString();
        switch (v.getId()) {
            case R.id.insert_button:
                if (mName.isEmpty()) {
                    Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else if (mPhone.isEmpty()) {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    insertPhone(mName, mPhone);
                }
                break;
            case R.id.query_button:
                queryPhone();
                break;
        }
    }


    private void insertPhone(String mName, String mPhone) {
        List<PhoneBean> mPhones = new ArrayList<>();
        mPhones.add(new PhoneBean(mPhone, mName, new Date()));
        basePresenter.insertAll(getApplicationContext(),mPhones);
        insertNameEdit.setText("");
        insertPhoneEdit.setText("");
    }


    private void queryPhone() {
        basePresenter.getAllPhone(getApplicationContext());
    }


    @Override
    public void showAllPhone(List<PhoneBean> phoneBean) {

        /*
        如果直接list = phoneBean，这时list对象已经不是setAdapter时的list对象了，所以无法使用mAdapter.notifyDataSetChanged();

        使用mAdapter.notifyDataSetChanged(); 1.需要是同一个RecyclerView(如被销毁后重新创建时已不同) 2.需要是同一个数据源(如new一个list再赋值)

         */

        //1.遍历赋值解决

/*
list.clear();
for(int i = 0;i<phoneBean.size(); i++){
            PhoneBean phone = new PhoneBean(phoneBean.get(i).getPhone(),phoneBean.get(i).getName(),phoneBean.get(i).getDate());
            list.add(phone);
        }*/

        //清空再add解决
        list.clear();
        list.addAll(phoneBean);
        mAdapter.notifyDataSetChanged();
    }
}
