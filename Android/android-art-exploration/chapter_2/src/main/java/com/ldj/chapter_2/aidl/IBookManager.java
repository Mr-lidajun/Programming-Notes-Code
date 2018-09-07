package com.ldj.chapter_2.aidl;

import android.os.IInterface;

/**
 * @author lidajun
 * @date 2018/8/30
 */

public interface IBookManager extends IInterface {
    static final java.lang.String DESCRIPTOR = "com.ldj.chapter_2.aidl.IBookManager";

    static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    public java.util.List<com.ldj.chapter_2.aidl.Book> getBookList()
            throws android.os.RemoteException;

    public void addBook(com.ldj.chapter_2.aidl.Book book) throws android.os.RemoteException;

}
