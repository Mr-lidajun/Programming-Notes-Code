package com.ldj.chapter_2.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lidajun
 * @date 2018/8/30
 */

public class BookManagerService extends Service {
    private static final String TAG = "BMS";
    private ArrayList<Book> mBookList = new ArrayList();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }
    };

    public BookManagerService() {

    }

    @Nullable @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "---->远程服务启动");
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "Ios"));
    }
}
