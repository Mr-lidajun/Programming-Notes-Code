import java.util.Random;

interface 客户 {
    boolean isVIP();
}

class 商户 {
    public double 折后价格 (double 原价, 客户 某客户) {
        return 原价 * 0.8d;
    }
}

class 奸商 extends 商户 {
    @Override
    public double 折后价格 (double 原价, 客户 某客户) {
        if (某客户.isVIP()) {                         // invokeinterface      
            return 原价 * 价格歧视 ();                    // invokestatic
        } else {
            return super. 折后价格 (原价, 某客户);          // invokespecial
        }
    }
    public static double 价格歧视 () {
        // 咱们的杀熟算法太粗暴了，应该将客户城市作为随机数生成器的种子。
        return new Random()                          // invokespecial
                .nextDouble()                         // invokevirtual
                + 0.8d;
    }
}