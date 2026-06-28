package Ex5_Decorator;

public class DecoratorTest {
    public static void main(String[] args) {
        String alert = "Server CPU usage exceeded 90%!";

        System.out.println("=== Email only ===");
        Notifier emailOnly = new EmailNotifier();
        emailOnly.send(alert);

        System.out.println("\n=== Email + SMS ===");
        Notifier emailSMS = new SMSNotifierDecorator(new EmailNotifier());
        emailSMS.send(alert);

        System.out.println("\n=== Email + SMS + Slack ===");
        Notifier allChannels = new SlackNotifierDecorator(
                                   new SMSNotifierDecorator(
                                       new EmailNotifier()));
        allChannels.send(alert);

        System.out.println("\n=== Email + WhatsApp + Slack ===");
        Notifier critical = new SlackNotifierDecorator(
                                new WhatsAppNotifierDecorator(
                                    new EmailNotifier()));
        critical.send(alert);
    }
}
