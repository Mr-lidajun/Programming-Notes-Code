package com.ldj.chapter_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.ldj.chapter_2.aidl.Book;
import com.ldj.chapter_2.aidl.BookManagerActivity;
import com.ldj.chapter_2.model.User;
import com.ldj.chapter_2.utils.MyConstants;
import com.ldj.chapter_2.utils.MyUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserManager.sUserId = 2;
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BookManagerActivity.class);
                User user = new User(0, "jake", true);
                user.book = new Book();
                intent.putExtra("extra_user", (Serializable) user);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "UserManage.sUserId=" + UserManager.sUserId);
        persistToFile();

        super.onResume();
    }

    private void persistToFile() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                User user = new User(1, "hello world", false);
                File dir = new File(MyConstants.CHAPTER_2_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File cachedFile = new File(MyConstants.CACHE_FILE_PATH);
                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(
                            new FileOutputStream(cachedFile));
                    objectOutputStream.writeObject(user);
                    Log.d(TAG, "persist user:" + user);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    MyUtils.close(objectOutputStream);
                }
            }
        }).start();
    }
}
