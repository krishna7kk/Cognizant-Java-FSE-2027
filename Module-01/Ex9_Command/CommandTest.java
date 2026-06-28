package Ex9_Command;

public class CommandTest {
    public static void main(String[] args) {
        Light livingRoom = new Light("Living Room");
        Light bedroom    = new Light("Bedroom");

        Command livingOn  = new LightOnCommand(livingRoom);
        Command livingOff = new LightOffCommand(livingRoom);
        Command bedOn     = new LightOnCommand(bedroom);

        RemoteControl remote = new RemoteControl();

        System.out.println("=== Pressing buttons ===");
        remote.pressButton(livingOn);
        remote.pressButton(bedOn);
        remote.pressButton(livingOff);

        System.out.println("\n=== Undo last 3 actions ===");
        remote.pressUndo();
        remote.pressUndo();
        remote.pressUndo();
        remote.pressUndo(); // nothing left
    }
}
