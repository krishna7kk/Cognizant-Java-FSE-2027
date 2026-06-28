package Ex8_Strategy;

// Strategy Interface
interface PaymentStrategy {
    void pay(double amount);
}

// Concrete Strategy 1
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    public CreditCardPayment(String cardNumber) { this.cardNumber = cardNumber; }
    public void pay(double amount) {
        System.out.printf("[CreditCard] ₹%.2f charged to card ending %s%n",
                amount, cardNumber.substring(cardNumber.length() - 4));
    }
}

// Concrete Strategy 2
class PayPalPayment implements PaymentStrategy {
    private String email;
    public PayPalPayment(String email) { this.email = email; }
    public void pay(double amount) {
        System.out.printf("[PayPal] ₹%.2f sent from account: %s%n", amount, email);
    }
}

// Concrete Strategy 3
class UPIPayment implements PaymentStrategy {
    private String upiId;
    public UPIPayment(String upiId) { this.upiId = upiId; }
    public void pay(double amount) {
        System.out.printf("[UPI] ₹%.2f transferred via UPI ID: %s%n", amount, upiId);
    }
}

// Context
class PaymentContext {
    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void executePayment(double amount) {
        if (strategy == null) throw new IllegalStateException("Payment strategy not set!");
        strategy.pay(amount);
    }
}
