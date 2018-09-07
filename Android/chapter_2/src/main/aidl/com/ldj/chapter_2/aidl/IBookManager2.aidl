// IBookManager.aidl
package com.ldj.chapter_2.aidl;

// 必须手动导包
import com.ldj.chapter_2.aidl.Book;
import com.ldj.chapter_2.aidl.IOnNewBookArrivedListener;

interface IBookManager2 {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
