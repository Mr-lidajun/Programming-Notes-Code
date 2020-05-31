import java.lang.ref.SoftReference;

import javax.sound.midi.Soundbank;

public class SoftReferenceNormal {
    static class SoftObject {
        byte[] data = new byte[120 * 1024 * 1024]; // 120M
    }

    public static void main(String[] args) {
        // ��������������Ӧ�ó���
        SoftReference<SoftObject> cachaRef = new SoftReference<>(new SoftObject());

        System.out.println("��һ��GCǰ �����ã�" + cachaRef.get());
        // ����һ��GC��鿴����Ļ������
        System.gc();
        System.out.println("��һ��GC�� ��Ӧ�ã�" + cachaRef.get());

        // �ٷ���һ��120M�Ķ��󣬿����������Ļ������
        SoftObject newSo = new SoftObject();
        System.out.println("�ٴη���100Mǿ���ö���� �����ã�" + cachaRef.get());
        
    }
}