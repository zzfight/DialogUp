package com.cc.zhangzheng.dialogup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by zz on 16/7/21.
 */
public class SimpleBottomDialog extends Dialog
{

    private Context context;
    private View parentView;
    private List<PopWindowAdapter.PopBean> dataList;
    private OnPopItemClickListener listener;
    private ListView lv;
    private View viewTop;//title视图
    private View viewBottom;
    private PopWindowAdapter adapter;
    private View mTopView;//头布局的resourceId
    private View mBottomView;
    private View mBackgroundView;

    //定义Listview的Item点击回调接口
    public interface OnPopItemClickListener {
        void onPopItemClick(View view, int position);
    }




    public SimpleBottomDialog(Context context ,
                              OnPopItemClickListener listener,
                              List<PopWindowAdapter.PopBean> dataList,
                              View bottomView ,
                              View topView)
    {
        this(context, R.style.Theme_Dialog_From_Bottom, listener,  dataList, bottomView, topView);
        // TODO Auto-generated constructor stub

    }

    public SimpleBottomDialog(Context context, int theme,
                              OnPopItemClickListener listener,
                              List<PopWindowAdapter.PopBean> dataList,
                              View bottomView ,
                              View topView)
    {
        super(context, theme);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.listener = listener;
        this.dataList = dataList;
        this.mBottomView = bottomView;
        this.mTopView = topView;
//        this.parentView = parentView;
        init();
    }




    private void init()
    {
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        parentView = LayoutInflater.from(context).inflate(R.layout.dialog_simple_bottom,null);
        setContentView(parentView);
        lv = (ListView) parentView.findViewById(R.id.lv_popwindow);

        initViews();
        initValues();
    }

    private void initViews()
    {
        viewTop = parentView.findViewById(R.id.view_line1);//头部
        viewBottom = parentView.findViewById(R.id.view_line);
        adapter = new PopWindowAdapter(context, dataList, false);
        adapter.setListener(new PopWindowAdapter.OnItemButtonClickedListener() {
            @Override
            public void onItemButtonClicked(int positon) {
                PopWindowAdapter.PopBean data = dataList.get(positon);
                if (data.isShowButton()) {
                    data.setIsShowButton(false);
                }
                adapter.setData(dataList);
                adapter.notifyDataSetChanged();
            }
        });
        lv.setAdapter(adapter);



        // 处理动态添加头布局
        FrameLayout topContainer = (FrameLayout) parentView.findViewById(R.id.top_container);
        if (mTopView != null) {
            topContainer.setVisibility(View.VISIBLE);
            topContainer.addView(mTopView);
            viewTop.setVisibility(View.VISIBLE);
        } else {
            topContainer.setVisibility(View.GONE);
            viewTop.setVisibility(View.GONE);
        }

        // 处理动态添加底布局
        LinearLayout bottomContainer = (LinearLayout)parentView.findViewById(R.id.bottom_container);
        if (mBottomView != null){
            bottomContainer.setVisibility(View.VISIBLE);
            bottomContainer.addView(mBottomView);
            viewBottom.setVisibility(View.VISIBLE);
        } else {
            bottomContainer.setVisibility(View.GONE);
            viewBottom.setVisibility(View.GONE);
        }

        mBackgroundView = parentView.findViewById(R.id.view_background);
        mBackgroundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onPopItemClick(view, position);
            }
        });

    }

    private void initValues()
    {
        // 不能写在init()中
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        lp.width = dm.widthPixels;//让dialog的宽占满屏幕的宽
        lp.gravity = Gravity.BOTTOM;//出现在底部
        window.setAttributes(lp);

    }
}