// ISecurityCenter.aidl
package com.ldj.chapter_2.aidl;

// Declare any non-default types here with import statements

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}
