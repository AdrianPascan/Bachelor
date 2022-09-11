package Commands;

import Controller.InterfaceController;

public class RunExampleCommand extends Command {
    private InterfaceController controller;

    public RunExampleCommand(String key, String description, InterfaceController controller){
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
//        try{
        controller.allStepExecution();
//        }
//        catch (MyException error){
//            System.out.println(error.getMessage());
//        }
    }
}
