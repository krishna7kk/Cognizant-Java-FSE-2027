package Ex6_Proxy;

// Subject interface
interface Image {
    void display();
}

// Real Subject – expensive to create (simulates remote server load)
class RealImage implements Image {
    private String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromServer();
    }

    private void loadFromServer() {
        System.out.println("[RealImage] Loading '" + filename + "' from remote server...");
        // Simulate network delay
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        System.out.println("[RealImage] '" + filename + "' loaded.");
    }

    public void display() {
        System.out.println("[RealImage] Displaying '" + filename + "'");
    }
}

/**
 * Proxy – implements lazy initialization and caching.
 * RealImage is only created on first display() call.
 * Subsequent calls reuse the cached instance.
 */
class ProxyImage implements Image {
    private String    filename;
    private RealImage realImage = null;  // null until first access (lazy)

    public ProxyImage(String filename) {
        this.filename = filename;
        System.out.println("[ProxyImage] Proxy created for '" + filename + "' (not loaded yet)");
    }

    public void display() {
        if (realImage == null) {
            System.out.println("[ProxyImage] Cache miss – initialising RealImage...");
            realImage = new RealImage(filename);   // lazy init
        } else {
            System.out.println("[ProxyImage] Cache hit – reusing loaded image.");
        }
        realImage.display();
    }
}
