// IBookManager.aidl
package com.ldj.chapter_2.aidl;

// 必须手动导包
import com.ldj.chapter_2.aidl.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
