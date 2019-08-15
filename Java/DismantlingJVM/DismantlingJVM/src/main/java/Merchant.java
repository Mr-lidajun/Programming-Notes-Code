public class Merchant<T extends Customer> {
    public Number actionPrice(double price, T customer) {
        return price * 0.8;
    }
}
