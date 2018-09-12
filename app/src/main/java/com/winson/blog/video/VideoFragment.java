package com.winson.blog.video;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.IOException;

public class VideoFragment extends Fragment {

    public static final String TAG = VideoFragment.class.getSimpleName();

    String TEST_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testmp4.mp4";
    int mIndex = 0;
    String path1 = TEST_PATH;
    String[] paths = new String[]{TEST_PATH, TEST_PATH, TEST_PATH, TEST_PATH, TEST_PATH, TEST_PATH};

    boolean destory;
    Handler mHandler;
    Runnable mPlayRun;
    FrameLayout content;
    TextureView textureView;
    ImageView frameImage;
    MediaPlayer mediaPlayer;
    Bitmap lastFrameBitmap;

    public void updateResources(String[] paths) {
        this.paths = paths;
        if(mHandler != null && mPlayRun!= null){
            mHandler.post(mPlayRun);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
        mediaPlayer = new MediaPlayer();
        mPlayRun = new Runnable() {

            @Override
            public void run() {

                if (mediaPlayer == null || destory) {
                    return;

                }
                mediaPlayer.pause();
                mediaPlayer.reset();

                try {
                    String path = paths[mIndex % paths.length];
                    mIndex++;

                    mediaPlayer.setDataSource(getActivity(), Uri.parse(path));
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                        @Override
                        public void onPrepared(MediaPlayer arg0) {
                            mediaPlayer.start();
                            frameImage.setVisibility(View.GONE);
                        }
                    });
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            frameImage.setVisibility(View.VISIBLE);
                            Bitmap currentFrameBitmap = textureView.getBitmap();
                            frameImage.setImageBitmap(currentFrameBitmap);
                            if (lastFrameBitmap != null) {
                                lastFrameBitmap.recycle();
                            }
                            lastFrameBitmap = currentFrameBitmap;

                            mHandler.post(mPlayRun);
                        }
                    });
                    mediaPlayer.prepareAsync();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
    }


    public void release() {
        mHandler.removeCallbacks(mPlayRun);
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }

    public Bitmap getBitmap() {
        return textureView == null ? null : textureView.getBitmap();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        release();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        content = new FrameLayout(getActivity());

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.LEFT | Gravity.TOP;

        textureView = new TextureView(getActivity());
        textureView.setLayoutParams(lp);
        content.addView(textureView);

        frameImage = new ImageView(getActivity());
        frameImage.setScaleType(ImageView.ScaleType.FIT_XY);
        frameImage.setLayoutParams(lp);
        content.addView(frameImage);

        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Surface s = new Surface(surface);
                mediaPlayer.setSurface(s);

            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        });

        return content;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        testPlay();
    }

    public void testPlay() {
//        mediaPlayer.pause();
//        mediaPlayer.reset();
//
//        try {
//            mediaPlayer.setDataSource(getActivity(), Uri.parse(TEST_PATH));
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        mHandler.post(mPlayRun);
    }


}
