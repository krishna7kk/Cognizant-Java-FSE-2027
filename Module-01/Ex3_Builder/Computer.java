package Ex3_Builder;

/**
 * Builder Pattern – Computer
 * Allows step-by-step construction of complex objects.
 * Avoids telescoping constructors and makes optional fields clear.
 */
public class Computer {

    // Required attributes
    private final String cpu;
    private final String ram;
    private final String storage;

    // Optional attributes
    private final String gpu;
    private final String os;
    private final boolean bluetoothEnabled;
    private final boolean wifiEnabled;

    // Private constructor – only Builder can instantiate
    private Computer(Builder builder) {
        this.cpu              = builder.cpu;
        this.ram              = builder.ram;
        this.storage          = builder.storage;
        this.gpu              = builder.gpu;
        this.os               = builder.os;
        this.bluetoothEnabled = builder.bluetoothEnabled;
        this.wifiEnabled      = builder.wifiEnabled;
    }

    @Override
    public String toString() {
        return "Computer {" +
               "\n  CPU        = " + cpu +
               "\n  RAM        = " + ram +
               "\n  Storage    = " + storage +
               "\n  GPU        = " + (gpu != null ? gpu : "Integrated") +
               "\n  OS         = " + (os != null ? os : "None") +
               "\n  Bluetooth  = " + bluetoothEnabled +
               "\n  WiFi       = " + wifiEnabled +
               "\n}";
    }

    // ===================== STATIC BUILDER =====================
    public static class Builder {
        // Required
        private final String cpu;
        private final String ram;
        private final String storage;

        // Optional – defaults
        private String  gpu              = null;
        private String  os               = null;
        private boolean bluetoothEnabled = false;
        private boolean wifiEnabled      = false;

        public Builder(String cpu, String ram, String storage) {
            this.cpu     = cpu;
            this.ram     = ram;
            this.storage = storage;
        }

        public Builder gpu(String gpu)                    { this.gpu = gpu; return this; }
        public Builder os(String os)                      { this.os  = os;  return this; }
        public Builder bluetooth(boolean bt)              { this.bluetoothEnabled = bt; return this; }
        public Builder wifi(boolean wifi)                 { this.wifiEnabled = wifi; return this; }

        public Computer build() { return new Computer(this); }
    }
}
