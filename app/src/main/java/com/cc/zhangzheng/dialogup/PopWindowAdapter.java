package com.cc.zhangzheng.dialogup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alston on 16/7/19.
 */
public class PopWindowAdapter extends BaseAdapter {
    private Context context;
    private List<PopBean> dataList;
    private LayoutInflater inflater;
    private boolean isShowImg = false;

    private OnItemButtonClickedListener mListener;

    public PopWindowAdapter(){

    }

    public PopWindowAdapter(Context context, List<PopBean> dataList, boolean isShowImg) {
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
        this.isShowImg = isShowImg;
    }



    public void setListener(OnItemButtonClickedListener listener) {
        this.mListener = listener;
    }

    public void setData(List<PopBean> dataList){
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (dataList != null) {
            count = dataList.size();
        }
        return count;
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (dataList == null || dataList.size() <= i) {
            return null;
        }
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.listview_popwindow_item, null);
            holder = new ViewHolder();
            holder.mIvIcon = (ImageView) view.findViewById(R.id.iv_icon);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_title);
            holder.v_line = (View) view.findViewById(R.id.v_line);
            holder.mBt = (Button) view.findViewById(R.id.bt);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        PopBean data = dataList.get(i);
        holder.mIvIcon.setImageResource(data.getIcon_res());
        holder.tv_name.setText(data.getTitle());
        initButton(holder, data, i);


        if (dataList.size() - 1 == i) {
            holder.v_line.setVisibility(View.INVISIBLE);
            holder.tv_name.setBackground(context.getResources().getDrawable(R.drawable.selector_bottom_half));
        } else {
            holder.v_line.setVisibility(View.VISIBLE);
            holder.tv_name.setBackground(context.getResources().getDrawable(R.drawable.list_gray_item));
        }



        return view;
    }


    private void initButton(ViewHolder holder, PopBean data, final int position) {
        if (!data.isShowButton()) {
            holder.mBt.setVisibility(View.GONE);
            return;
        }
        holder.mBt.setVisibility(View.VISIBLE);
        holder.mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) {
                    return;
                }
                mListener.onItemButtonClicked(position);
            }
        });

    }


    private class ViewHolder {
        ImageView mIvIcon;
        TextView tv_name;
        View v_line;
        Button mBt;
    }

    public interface OnItemButtonClickedListener {
        void onItemButtonClicked(int positon);
    }

//    -------------------------Bean----------------------

    public class PopBean {
        private String title;
        private int icon_res;
        private boolean isShowButton = false;

        public boolean isShowButton() {
            return isShowButton;
        }

        public void setIsShowButton(boolean isShowButton) {
            this.isShowButton = isShowButton;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIcon_res() {
            return icon_res;
        }

        public void setIcon_res(int icon_res) {
            this.icon_res = icon_res;
        }

        public PopBean(String title, int icon_res) {
            this.title = title;
            this.icon_res = icon_res;
        }
    }
}
