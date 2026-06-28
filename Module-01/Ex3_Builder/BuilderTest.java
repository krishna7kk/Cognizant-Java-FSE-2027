package Ex3_Builder;

public class BuilderTest {
    public static void main(String[] args) {

        Computer gamingPC = new Computer.Builder("Intel i9-13900K", "32GB DDR5", "2TB NVMe SSD")
                .gpu("NVIDIA RTX 4090")
                .os("Windows 11")
                .bluetooth(true)
                .wifi(true)
                .build();

        Computer officePC = new Computer.Builder("Intel i5-12400", "16GB DDR4", "512GB SSD")
                .os("Windows 10")
                .wifi(true)
                .build();

        Computer serverNode = new Computer.Builder("AMD EPYC 9654", "256GB ECC RAM", "10TB HDD RAID")
                .os("Ubuntu Server 22.04")
                .build();

        System.out.println("=== Gaming PC ===");
        System.out.println(gamingPC);

        System.out.println("\n=== Office PC ===");
        System.out.println(officePC);

        System.out.println("\n=== Server Node ===");
        System.out.println(serverNode);
    }
}
