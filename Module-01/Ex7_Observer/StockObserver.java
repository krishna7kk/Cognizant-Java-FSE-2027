package Ex7_Observer;

import java.util.ArrayList;
import java.util.List;

// Observer interface
interface Observer {
    void update(String stockName, double price);
}

// Subject interface
interface Stock {
    void registerObserver(Observer o);
    void deregisterObserver(Observer o);
    void notifyObservers();
}

// Concrete Subject
class StockMarket implements Stock {
    private List<Observer> observers = new ArrayList<>();
    private String stockName;
    private double price;

    public StockMarket(String stockName, double price) {
        this.stockName = stockName;
        this.price     = price;
    }

    public void registerObserver(Observer o)   { observers.add(o);    System.out.println("  Registered: " + o.getClass().getSimpleName()); }
    public void deregisterObserver(Observer o) { observers.remove(o); System.out.println("  Deregistered: " + o.getClass().getSimpleName()); }
    public void notifyObservers()              { for (Observer o : observers) o.update(stockName, price); }

    // Called when price changes – triggers notification
    public void setPrice(double newPrice) {
        System.out.printf("%nStockMarket: %s price changed %.2f -> %.2f%n", stockName, this.price, newPrice);
        this.price = newPrice;
        notifyObservers();
    }
}

// Concrete Observers
class MobileApp implements Observer {
    private String user;
    public MobileApp(String user) { this.user = user; }
    public void update(String stock, double price) {
        System.out.printf("  [MobileApp-%s] ALERT: %s is now ₹%.2f%n", user, stock, price);
    }
}

class WebApp implements Observer {
    public void update(String stock, double price) {
        System.out.printf("  [WebApp] Dashboard updated: %s = ₹%.2f%n", stock, price);
    }
}
