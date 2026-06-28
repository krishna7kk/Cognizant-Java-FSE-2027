package Ex5_Decorator;

// Concrete Component
class EmailNotifier implements Notifier {
    public void send(String message) {
        System.out.println("[EMAIL] Sending: " + message);
    }
}

// Abstract Decorator
abstract class NotifierDecorator implements Notifier {
    protected Notifier wrappee;
    public NotifierDecorator(Notifier notifier) { this.wrappee = notifier; }
    public void send(String message)            { wrappee.send(message); }
}

// Concrete Decorator 1
class SMSNotifierDecorator extends NotifierDecorator {
    public SMSNotifierDecorator(Notifier notifier) { super(notifier); }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("[SMS] Sending: " + message);
    }
}

// Concrete Decorator 2
class SlackNotifierDecorator extends NotifierDecorator {
    public SlackNotifierDecorator(Notifier notifier) { super(notifier); }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("[SLACK] Posting: " + message);
    }
}

// Concrete Decorator 3
class WhatsAppNotifierDecorator extends NotifierDecorator {
    public WhatsAppNotifierDecorator(Notifier notifier) { super(notifier); }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("[WHATSAPP] Sending: " + message);
    }
}
