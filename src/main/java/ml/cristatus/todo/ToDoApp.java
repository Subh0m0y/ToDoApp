package ml.cristatus.todo;

import ml.cristatus.todo.model.ToDoItem;
import ml.cristatus.todo.repository.InMemoryToDoRepository;
import ml.cristatus.todo.repository.ToDoRepository;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
public class ToDoApp {
    public static void main(String[] args) {
        System.out.println(TITLE);
        REPL(new Scanner(System.in), System.out);
    }


    private static final String TITLE = "\n\n========ToDo App========\n\n";
    private static final String HELP = "The following commands are recognised:" +
            "\n1. help\n\tDisplay this help message." +
            "\n2. add <name>\n\tAdd a new Todo task with the given name and " +
            "also displays its corresponding ID." +
            "\n3. get <id>\n\tDisplays the task with the given id." +
            "\n4. mark <id>\n\tToggles the given task as completed or " +
            "incomplete." +
            "\n5. print\n\tDisplays all tasks in order of their creation." +
            "\n6. update <id> <new text>\n\tUpdates the item with the given " +
            "id to store the new text." +
            "\n7. del <id>\n\tDeletes the task with the given id." +
            "\n8. exit\n\tExit the program.";
    private static final String PROMPT = ">> ";
    private static final String ABSENT = "No item found with the given ID.";

    private static void REPL(Scanner in, PrintStream out) {
        ToDoRepository repository = new InMemoryToDoRepository();
        out.println(HELP);
        //noinspection InfiniteLoopStatement
        while (true) {
            out.print(PROMPT);
            processInput(repository, in, out);
        }
    }

    private static void processInput(ToDoRepository repository,
                                     Scanner in,
                                     PrintStream out) {
        String command = in.next().toLowerCase();
        switch (command) {
            case "help":
                out.println(HELP);
                break;
            case "exit":
                System.exit(0);
            case "add":
                addItem(repository, in, out);
                break;
            case "get":
                ToDoItem item = input(repository, in);
                out.println(item != null ? item : ABSENT);
                break;
            case "mark":
                mark(repository, in, out);
                break;
            case "update":
                update(repository, in, out);
                break;
            case "del":
                delete(repository, in, out);
                break;
            case "print":
                out.println("The tasks are :");
                for (ToDoItem toDoItem : repository.findAll()) {
                    out.println(toDoItem);
                }
                break;
            default:
                out.println("Unrecognised command. Try again.");
        }
    }

    private static ToDoItem input(ToDoRepository repository, Scanner in) {
        Long id = in.nextLong();
        return repository.findById(id);
    }

    private static void addItem(ToDoRepository repository,
                                Scanner in,
                                PrintStream out) {
        ToDoItem item = new ToDoItem(in.nextLine());
        Long newId = repository.insert(item);
        out.println("New item added with id = " + newId);
    }

    private static void mark(ToDoRepository repository,
                             Scanner in,
                             PrintStream out) {
        ToDoItem item = input(repository, in);
        if (item == null) {
            out.println(ABSENT);
            return;
        }
        item.setCompleted(!item.isCompleted());
        out.println("State of item with id = " + item.getId() + " flipped.");
        repository.update(item);
    }

    private static void update(ToDoRepository repository,
                               Scanner in,
                               PrintStream out) {
        ToDoItem item = input(repository, in);
        if (item == null) {
            out.println(ABSENT);
            return;
        }
        item.setName(in.nextLine());
        out.println("Updated item #" + item.getId() + ": " + item);
        repository.update(item);
    }

    private static void delete(ToDoRepository repository,
                               Scanner in,
                               PrintStream out) {
        ToDoItem item = input(repository, in);
        if (item == null) {
            out.println(ABSENT);
            return;
        }
        out.println("Deleted \"" + item + "\"");
        repository.delete(item);
    }
}
