// IOnNewBookArrivedListener.aidl
package com.ldj.chapter_2.aidl;
import com.ldj.chapter_2.aidl.Book;

// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
