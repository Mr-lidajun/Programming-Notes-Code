import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Set;

import javax.sound.midi.Soundbank;

public class SoftReferenceTest2 {
    static class MyBigObject {
        byte[] data = new byte[1024]; // 1KB
    }

    public static int removedSoftRefs = 0;
    public static int CACHE_INITAL_CAPACITY = 100 * 1024; // 100M
    // ��̬���ϱ��������ã��ᵼ����Щ�����ö������޷�����������������
    public static Set<SoftReference<MyBigObject>> cache = new HashSet<>(CACHE_INITAL_CAPACITY);
    public static ReferenceQueue<MyBigObject> referenceQueue = new ReferenceQueue<>();

    public static void main(String[] args) {
        for (int i = 0; i < CACHE_INITAL_CAPACITY; i++) {
            MyBigObject obj = new MyBigObject();
            cache.add(new SoftReference<MyBigObject>(obj, referenceQueue));
            clearUselessReferences();
            if (i % 10000 == 0) {
                System.out.println("size of cache: " + cache.size());
            }
        }
        System.out.println("End, removed soft reference = " + removedSoftRefs);
    }

    public static void clearUselessReferences() {
        Reference<? extends MyBigObject> ref = referenceQueue.poll();
        while (ref != null) {
            if (cache.remove(ref)) {
                removedSoftRefs++;
            }
            ref = referenceQueue.poll();
        }
    }
}