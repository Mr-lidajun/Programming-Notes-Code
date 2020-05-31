import java.lang.ref.SoftReference;

import javax.sound.midi.Soundbank;

public class SoftReferenceNormal {
    static class SoftObject {
        byte[] data = new byte[120 * 1024 * 1024]; // 120M
    }

    public static void main(String[] args) {
        // 将缓存数据用软应用持有
        SoftReference<SoftObject> cachaRef = new SoftReference<>(new SoftObject());

        System.out.println("第一次GC前 软引用：" + cachaRef.get());
        // 进行一次GC后查看对象的回收情况
        System.gc();
        System.out.println("第一次GC后 软应用：" + cachaRef.get());

        // 再分配一个120M的对象，看看缓存对象的回收情况
        SoftObject newSo = new SoftObject();
        System.out.println("再次分配100M强引用对象后 软引用：" + cachaRef.get());
        
    }
}