package Ex9_Command;

import java.util.ArrayDeque;
import java.util.Deque;

// Command Interface
interface Command {
    void execute();
    void undo();
}

// Receiver
class Light {
    private String location;
    public Light(String location) { this.location = location; }
    public void turnOn()  { System.out.println("[Light] " + location + " light is ON"); }
    public void turnOff() { System.out.println("[Light] " + location + " light is OFF"); }
}

// Concrete Commands
class LightOnCommand implements Command {
    private Light light;
    public LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.turnOn(); }
    public void undo()    { light.turnOff(); }
}

class LightOffCommand implements Command {
    private Light light;
    public LightOffCommand(Light light) { this.light = light; }
    public void execute() { light.turnOff(); }
    public void undo()    { light.turnOn(); }
}

// Invoker – supports undo history
class RemoteControl {
    private Deque<Command> history = new ArrayDeque<>();

    public void pressButton(Command command) {
        command.execute();
        history.push(command);
    }

    public void pressUndo() {
        if (history.isEmpty()) {
            System.out.println("[Remote] Nothing to undo.");
            return;
        }
        Command last = history.pop();
        System.out.println("[Remote] Undoing last command...");
        last.undo();
    }
}
