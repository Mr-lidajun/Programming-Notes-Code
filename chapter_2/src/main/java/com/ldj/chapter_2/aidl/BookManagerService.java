package com.ldj.chapter_2.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author lidajun
 * @date 2018/8/30
 */

public class BookManagerService extends Service {
    private static final String TAG = "BMS";
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private Binder mBinder = new IBookManager2.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d(TAG, "---->Binder线程池: " + Thread.currentThread().getName());
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
