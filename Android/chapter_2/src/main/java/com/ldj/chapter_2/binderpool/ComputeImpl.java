package com.ldj.chapter_2.binderpool;

import android.os.RemoteException;
import com.ldj.chapter_2.aidl.ICompute;

/**
 *
 * @author lidajun
 * @date 2018/9/6
 */

public class ComputeImpl extends ICompute.Stub {
    @Override public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
