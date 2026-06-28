package Ex8_Strategy;

public class StrategyTest {
    public static void main(String[] args) {
        PaymentContext ctx = new PaymentContext();
        double orderTotal = 2499.00;

        System.out.println("Order Total: ₹" + orderTotal);

        System.out.println("\n-- Customer chooses Credit Card --");
        ctx.setStrategy(new CreditCardPayment("4111111111111234"));
        ctx.executePayment(orderTotal);

        System.out.println("\n-- Customer switches to PayPal --");
        ctx.setStrategy(new PayPalPayment("customer@gmail.com"));
        ctx.executePayment(orderTotal);

        System.out.println("\n-- Customer switches to UPI --");
        ctx.setStrategy(new UPIPayment("customer@upi"));
        ctx.executePayment(orderTotal);
    }
}
