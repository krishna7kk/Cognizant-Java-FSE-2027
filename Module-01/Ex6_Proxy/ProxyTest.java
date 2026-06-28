package Ex6_Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        Image img1 = new ProxyImage("photo_vacation.jpg");
        Image img2 = new ProxyImage("profile_picture.png");

        System.out.println("\n-- First access (loads from server) --");
        img1.display();

        System.out.println("\n-- Second access (from cache) --");
        img1.display();

        System.out.println("\n-- First access of different image --");
        img2.display();

        System.out.println("\n-- img2 again (cached) --");
        img2.display();
    }
}
