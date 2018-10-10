package com.liweidong.mymvp.ui.room.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liweidong.mymvp.R;
import com.liweidong.mymvp.base.BaseDialog;
import com.liweidong.mymvp.model.bean.PhoneBean;
import com.liweidong.mymvp.model.db.PhoneDatabase;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:李炜东
 * Date:2018/9/12.
 * WX:17337162963
 * Email:18037891572@163.com
 */

public class EditDialog extends BaseDialog implements View.OnClickListener {


    EditText updateNameEdit;
    EditText updatePhoneEdit;

    Button updateButton;
    Button deleteButton;

    private PhoneBean mPhoneBean;

    private OnRefreshDataListener mListener;

    /*
    重写设置窗口转场动画
     */
    protected int setWindowAnimationsStyle(){
        super.setWindowAnimationsStyle();
        return R.style.dialogAnim;
    }

/*    *//*
    重写onStart自己计算屏幕宽度并设置到对话框
     *//*
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }*/


    /*
重写onStart()对DialogFragment初始化
     */
    public void onStart() {
        super.onStart();

        //点击DialogFragment外不隐藏
        //setCanceledOnTouchOutside(false);

        //设置DialogFragment宽高
        setDialogWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        //DialogFragment显示位置
        setGravity(Gravity.BOTTOM);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_edit_info;
    }

    @Override
    protected void initView(View view) {
        updateNameEdit = view.findViewById(R.id.update_name_edit);
        updatePhoneEdit = view.findViewById(R.id.update_phone_edit);
        view.findViewById(R.id.update_button).setOnClickListener(this);
        view.findViewById(R.id.delete_button).setOnClickListener(this);
    }

    @Override
    protected void loadData(Bundle bundle) {
        if (bundle != null) {
            mPhoneBean = bundle.getParcelable("PhoneBean");
            updateNameEdit.setText(mPhoneBean.getName());
            updatePhoneEdit.setText(mPhoneBean.getPhone());
        }
    }

    public void onClick(View v) {
        String mName = updateNameEdit.getText().toString();
        String mPhone = updatePhoneEdit.getText().toString();
        switch (v.getId()) {
            case R.id.update_button:
                if (mName.isEmpty()) {
                    Toast.makeText(getActivity(), "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else if (mPhone.isEmpty()) {
                    Toast.makeText(getActivity(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    updatePhone(mName, mPhone);
                }
                break;
            case R.id.delete_button:
                deletePhone();
                break;
        }
    }


    private void updatePhone(String name, String phone) {
        mPhoneBean.setName(name);
        mPhoneBean.setPhone(phone);
        mPhoneBean.setDate(new Date());
        PhoneDatabase.getDefault(getActivity().getApplicationContext()).getPhoneDao().update(mPhoneBean);
        dismiss();
        if (mListener != null) {
            mListener.onRefresh();
        }
    }

    private void deletePhone() {
        PhoneDatabase.getDefault(getActivity().getApplicationContext()).getPhoneDao().delete(mPhoneBean);
        dismiss();
        if (mListener != null) {
            mListener.onRefresh();
        }
    }


    public interface OnRefreshDataListener {
        void onRefresh();
    }

    public void setOnRefreshDataListener(OnRefreshDataListener listener) {
        this.mListener = listener;
    }


}
