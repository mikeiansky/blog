package com.winson.blog.jd_refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.winson.blog.R;
import com.winson.blog.base.BaseActivity;
import com.winson.blog.base.CommonAdapter;
import com.winson.blog.base.ViewHolder;
import com.winson.widget.pullrefreshlayout.OnPullRefreshListener;
import com.winson.widget.pullrefreshlayout.PullRefreshHeadLayout;
import com.winson.widget.pullrefreshlayout.PullRefreshLayout;
import com.winson.widget.pullrefreshlayout.PullRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2018/9/30
 * @Author Winson
 */
public class JDRefreshActivity extends BaseActivity {

    PullRefreshLayout pullRefreshLayout;
    PullRefreshListView pullRefreshListView;
    PullRefreshHeadLayout pullRefreshHeadLayout;

    View preRefreshContent, onRefreshContent;
    float density;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_jd_refresh);

        density = getResources().getDisplayMetrics().density;

        pullRefreshLayout = findViewById(R.id.pull_refresh_layout);
        pullRefreshListView = findViewById(R.id.pull_refresh_content);
        pullRefreshHeadLayout = findViewById(R.id.pull_refresh_head);

        preRefreshContent = findViewById(R.id.pre_refresh_content);
        onRefreshContent = findViewById(R.id.on_refresh_content);

        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add("position : " + i);
        }
        MyAdapter myAdapter = new MyAdapter(this, data);
        pullRefreshListView.setAdapter(myAdapter);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullRefreshLayout.refreshComplete(3000);
            }
        });

        pullRefreshHeadLayout.setOnPullRefreshListener(new OnPullRefreshListener() {
            @Override
            public void onPullProgressUpdate(int progress) {
                ImageView jdBoxImageView = preRefreshContent.findViewById(R.id.jd_box);
                jdBoxImageView.setTranslationX(-27 * density * progress / 100);
                jdBoxImageView.setTranslationY(27f * density * progress / 100);
            }

            @Override
            public void onRefresh() {
                preRefreshContent.setVisibility(View.GONE);
                onRefreshContent.setVisibility(View.VISIBLE);
                AnimationDrawable ad = (AnimationDrawable) pullRefreshHeadLayout.findViewById(R.id.jd_refresh_iv).getBackground();
                ad.start();
            }

            @Override
            public void onRefreshComplete() {
                preRefreshContent.setVisibility(View.VISIBLE);
                onRefreshContent.setVisibility(View.GONE);
                AnimationDrawable ad = (AnimationDrawable) pullRefreshHeadLayout.findViewById(R.id.jd_refresh_iv).getBackground();
                ad.stop();
            }
        });

    }

    class MyAdapter extends CommonAdapter<String> {

        public MyAdapter(Context context, List<String> datas) {
            super(context, R.layout.item_test_act, datas);
        }

        @Override
        public void convert(ViewHolder viewHolder, String obj, int position) {
            ((TextView) viewHolder.findViewById(R.id.test_act_title)).setText(obj);
        }
    }

}
