package com.wpy.stick;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wpy.stick.adapter.SimpleAdapter;
import com.wpy.stick.bean.City;
import com.wpy.stick.bean.CityUtil;
import com.wpy.stick.stick.PowerfulStickyDecoration;
import com.wpy.stick.stick.listener.PowerGroupListener;
import com.wpy.stick.uitil.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/9/23.
 */

public class BeautifulRecyclerViewActivity extends AppCompatActivity {
    @BindView(R.id.rv)
    RecyclerView mRv;

    RecyclerView.Adapter mAdapter;
    List<City> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_recycler_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //模拟数据
        dataList.addAll(CityUtil.getCityList());
        dataList.addAll(CityUtil.getCityList());

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRv.setLayoutManager(manager);
        PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder
                .init(new PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //获取组名，用于判断是否是同一组
                        if (dataList.size() > position) {
                            return dataList.get(position).getProvince();
                        }
                        return null;
                    }

                    @Override
                    public View getGroupView(final int position) {
                        //获取自定定义的组View
                        if (dataList.size() > position) {
                            View view = getLayoutInflater().inflate(R.layout.city_group, null, false);
                            TextView tv = (TextView) view.findViewById(R.id.tv);
                            tv.setText(dataList.get(position).getProvince());
                            ((ImageView) view.findViewById(R.id.iv)).setImageResource(dataList.get(position).getIcon());
                            return view;
                        } else {
                            return null;
                        }
                    }
                })
                .setGroupHeight(DensityUtil.dip2px(BeautifulRecyclerViewActivity.this, 40))   //设置高度
                .build();

        mRv.addItemDecoration(decoration);
        mAdapter = new SimpleAdapter(this, dataList);
        mRv.setAdapter(mAdapter);
    }
}
