package ml.cristatus.todo;

import ml.cristatus.todo.model.ToDoItem;
import ml.cristatus.todo.repository.ToDoRepository;
import ml.cristatus.todo.repository.ToDoRepositoryWithJSON;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Subhomoy Haldar
 * @version 0.3
 */
public class ToDoApp {
    public static void main(String[] args) {
        System.out.println(TITLE);
        REPL(new Scanner(System.in), System.out);
    }

    private static final String TITLE = "\n\n========ToDo App========\n\n";
    private static final String HELP = "The following commands are recognised:" +
            "\n - help\n\tDisplay this help message." +
            "\n - add <name>\n\tAdd a new Todo task with the given name and " +
            "also displays its corresponding ID." +
            "\n - get <id>\n\tDisplays the task with the given id." +
            "\n - mark <id>\n\tToggles the given task as completed or " +
            "incomplete." +
            "\n - print\n\tDisplays all tasks in order of their creation." +
            "\n - update <id> <new text>\n\tUpdates the item with the given " +
            "id to store the new text." +
            "\n - del <id>\n\tDeletes the task with the given id." +
            "\n - clear\n\tDeletes all COMPLETED tasks." +
            "\n - burn\n\tDeletes ALL tasks and empties the list." +
            "\n - exit\n\tExit the program.";
    private static final String PROMPT = ">> ";
    private static final String ABSENT = "No item found with the given ID.";

    private static void REPL(Scanner in, PrintStream out) {
        ToDoRepository repository = new ToDoRepositoryWithJSON();
        out.println(HELP);
        out.println("\n");
        printAll(repository, out);
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
                repository.save();
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
                printAll(repository, out);
                break;
            case "clear":
                clear(repository, out);
                break;
            case "burn":
                burn(repository, out);
                break;
            default:
                out.println("Unrecognised command. Try again.");
        }
    }

    private static ToDoItem input(ToDoRepository repository, Scanner in) {
        try {
            Long id = in.nextLong();
            return repository.findById(id);
        } catch (InputMismatchException exception) {
            in.nextLine();  // skip the line, start afresh
            return null;
        }
    }

    private static void printAll(ToDoRepository repository,
                                 PrintStream out) {
        List<ToDoItem> items = repository.findAll();
        if (items.isEmpty()) {
            out.println("No tasks defined. Add some to get started.");
            return;
        }
        out.println("The tasks are :");
        for (ToDoItem toDoItem : items) {
            out.println(toDoItem);
        }
    }

    private static void addItem(ToDoRepository repository,
                                Scanner in,
                                PrintStream out) {
        String next = in.nextLine().trim();
        Long newId = repository.insert(next);
        out.println("New item added with ID = " + newId);
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
        out.println("State of item with ID = " + item.getId() + " flipped.");
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
        String name = in.nextLine().trim();
        if (name.isEmpty()) {
            out.println("Please enter some text for the update.");
            return;
        }
        item.setName(name);
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

    private static void clear(ToDoRepository repository,
                              PrintStream out) {
        int count = 0;
        for (ToDoItem item : repository.findAll()) {
            if (item.isCompleted()) {
                repository.delete(item);
                count++;
            }
        }
        out.println("List cleared. Deleted " + count + " items.");
    }

    public static void burn(ToDoRepository repository,
                            PrintStream out) {
        int count = 0;
        for (ToDoItem item : repository.findAll()) {
            repository.delete(item);
            count++;
        }
        out.println("List emptied. Deleted " + count + " items.");
    }
}
