package com.cc.zhangzheng.dialogup;

import android.location.GpsStatus;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SimpleBottomDialog.OnPopItemClickListener {

    private Button button;
    private SimpleBottomDialog dialog;
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
                Toast.makeText(MainActivity.this, "弹出成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        button = (Button) findViewById(R.id.btn_0);
        rl = (RelativeLayout) findViewById(R.id.rl);


    }

    private void show() {
        List<PopWindowAdapter.PopBean> pops = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PopWindowAdapter popWindowAdapter = new PopWindowAdapter();
            PopWindowAdapter.PopBean pop =popWindowAdapter.new PopBean("item" + i, R.drawable.common_share_qq);
            pop.setIsShowButton(i % 2 == 0);
            pops.add(pop);
        }
        View topView = LayoutInflater.from(this).inflate(R.layout.popup_top_view, null);
        View bottomView = LayoutInflater.from(this).inflate(R.layout.popup_bottom_view, null);
        dialog = new SimpleBottomDialog(MainActivity.this, this, pops, bottomView, topView);
        dialog.show();
    }

    @Override
    public void onPopItemClick(View view, int position) {
        switch (position) {
            case 0:
                Log.d("tag>>>>>", "click index:" + position);
                break;
            case 1:
                Log.d("tag>>>>>", "click index:" + position);
                break;
            case 2:
                Log.d("tag>>>>>", "click index:" + position);
                break;
            case 3:
                Log.d("tag>>>>>", "click index:" + position);
                break;
            case 4:
                Log.d("tag>>>>>", "click index:" + position);
                break;
            default:
                break;
        }
    }
}