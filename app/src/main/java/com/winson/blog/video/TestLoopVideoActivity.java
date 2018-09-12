package com.winson.blog.video;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.winson.blog.R;
import com.winson.blog.base.BaseActivity;

public class TestLoopVideoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_loop_video);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.video_content, new VideoFragment())
                .commit();

    }
}
