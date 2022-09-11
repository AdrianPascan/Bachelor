import Controller.Controller;
import Repository.InMemoryRepository;
import Repository.RepositoryInterface;
import View.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        RepositoryInterface repository = new InMemoryRepository(100);
        Controller controller = new Controller(repository);
        ConsoleUI console = new ConsoleUI(controller);

        console.populate();
        console.start();
    }
}
