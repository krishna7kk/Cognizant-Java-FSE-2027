package Ex7_Observer;

public class ObserverTest {
    public static void main(String[] args) {
        StockMarket tcs = new StockMarket("TCS", 3800.00);

        Observer mobile1 = new MobileApp("Ravi");
        Observer mobile2 = new MobileApp("Priya");
        Observer web     = new WebApp();

        System.out.println("Registering observers...");
        tcs.registerObserver(mobile1);
        tcs.registerObserver(mobile2);
        tcs.registerObserver(web);

        tcs.setPrice(3850.00);
        tcs.setPrice(3790.50);

        System.out.println("\nDeregistering Ravi's mobile app...");
        tcs.deregisterObserver(mobile1);

        tcs.setPrice(3910.00);
    }
}
