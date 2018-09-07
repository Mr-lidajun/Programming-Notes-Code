package com.ldj.chapter_2.binderpool;

import android.os.RemoteException;
import com.ldj.chapter_2.aidl.ISecurityCenter;

/**
 *
 * @author lidajun
 * @date 2018/9/6
 */

public class SecurityCenterImpl extends ISecurityCenter.Stub {

    private static final char SECRET_CODE = '^';

    @Override public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
