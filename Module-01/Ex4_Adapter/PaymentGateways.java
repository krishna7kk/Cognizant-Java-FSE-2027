package Ex4_Adapter;

// Third-party gateway 1 – has its own method signature
class PayPalGateway {
    public void makePayment(double amount) {
        System.out.printf("PayPal: Payment of ₹%.2f processed via PayPal account.%n", amount);
    }
}

// Third-party gateway 2 – different method name
class StripeGateway {
    public void charge(double amount) {
        System.out.printf("Stripe: Card charged ₹%.2f via Stripe API.%n", amount);
    }
}

// Third-party gateway 3 – yet another signature
class RazorpayGateway {
    public void executePayment(String currency, double amount) {
        System.out.printf("Razorpay: %s %.2f payment executed via Razorpay.%n", currency, amount);
    }
}

// ==================== ADAPTERS ====================

class PayPalAdapter implements PaymentProcessor {
    private PayPalGateway gateway = new PayPalGateway();
    public void processPayment(double amount) { gateway.makePayment(amount); }
}

class StripeAdapter implements PaymentProcessor {
    private StripeGateway gateway = new StripeGateway();
    public void processPayment(double amount) { gateway.charge(amount); }
}

class RazorpayAdapter implements PaymentProcessor {
    private RazorpayGateway gateway = new RazorpayGateway();
    public void processPayment(double amount) { gateway.executePayment("INR", amount); }
}
