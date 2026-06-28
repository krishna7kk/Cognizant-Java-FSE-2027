package Ex4_Adapter;

public class AdapterTest {
    public static void main(String[] args) {
        PaymentProcessor[] processors = {
            new PayPalAdapter(),
            new StripeAdapter(),
            new RazorpayAdapter()
        };

        double orderAmount = 4999.00;
        System.out.println("Order total: ₹" + orderAmount);
        System.out.println("--- Processing via different gateways ---");

        for (PaymentProcessor processor : processors) {
            processor.processPayment(orderAmount);
        }
    }
}
