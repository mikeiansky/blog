package com.winson.blog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.winson.blog.base.BaseActivity;
import com.winson.blog.base.CommonAdapter;
import com.winson.blog.base.TestActItem;
import com.winson.blog.base.ViewHolder;
import com.winson.blog.video.TestLoopVideoActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    ListView testActListView;
    List<TestActItem> testActs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        testActListView = findViewById(R.id.list_test_act);

        testActs = new ArrayList<>();

        TestActItem testActItem = new TestActItem();
        testActItem.title = "Test Loop Video";
        testActItem.actClass = TestLoopVideoActivity.class;
        testActs.add(testActItem);

        final TestActListAdapter testActListAdapter = new TestActListAdapter(this, testActs);
        testActListView.setAdapter(testActListAdapter);
        testActListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TestActItem item = testActListAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, item.actClass);
                startActivity(intent);
            }
        });
    }

    class TestActListAdapter extends CommonAdapter<TestActItem> {

        public TestActListAdapter(Context context, List<TestActItem> datas) {
            super(context, R.layout.item_test_act, datas);
        }

        @Override
        public void convert(ViewHolder viewHolder, TestActItem data, int position) {
            ((TextView) viewHolder.findViewById(R.id.test_act_title)).setText(data.title);
        }

    }

}
