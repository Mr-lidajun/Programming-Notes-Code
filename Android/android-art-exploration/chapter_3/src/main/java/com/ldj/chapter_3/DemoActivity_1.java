package com.ldj.chapter_3;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import com.ldj.chapter_3.ui.HorizontalScrollViewEx;

public class DemoActivity_1 extends Activity {
    private static final String TAG = "DemoActivity_1";
    private HorizontalScrollViewEx mListContainer;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_1);
        Log.d(TAG, "onCreate");
        initView();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = (HorizontalScrollViewEx) findViewById(R.id.container);
    }
}
