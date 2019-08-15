import java.util.Random;

public class VIPOnlyMerchant extends Merchant<VIP> {

    @Override
    public Number actionPrice(double price, VIP customer) {
        if (customer.isVIP()) {
            return price * 价格歧视();
        } else {
            return super.actionPrice(price, customer);
        }
    }

    private static double 价格歧视() {
        return new Random().nextDouble() + 0.8d;
    }
}
