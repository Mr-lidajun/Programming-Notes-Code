package l04;

public class NaiveMerchant extends Merchant {

    public static void main(String[] args) {
        Merchant merchant = new NaiveMerchant();
        // price 必须定义成 Number 类型
        Number price = merchant.actionPrice(40, null);
        System.out.println(price);
    }

    @Override
    public Number actionPrice(double price, Customer customer) {
        return 0.9 * price;
    }
}

