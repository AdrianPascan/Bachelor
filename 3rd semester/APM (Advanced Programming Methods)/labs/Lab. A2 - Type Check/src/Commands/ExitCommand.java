package Commands;

public class ExitCommand extends Command {
    public ExitCommand(String key, String description){
        super(key, description);
    }

    @Override
    public void execute() {
        System.out.println("Closing application...\nApplication has been closed.");
        System.exit(0);
    }
}
