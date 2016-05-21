package ml.cristatus.todo.repository;

import com.google.gson.Gson;
import ml.cristatus.todo.model.ToDoItem;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * @author Subhomoy Haldar
 * @version 0.3
 */
public class ToDoRepositoryWithJSON implements ToDoRepository {

    private static final File DEFAULT_FILE = new File(
            System.getProperty("user.home") + "/Documents/toDoData.json"
    );

    private InMemoryToDoRepository internalRepository;

    public ToDoRepositoryWithJSON() {
        if (DEFAULT_FILE.exists()) {
            String data = getDataFrom(DEFAULT_FILE);
            internalRepository = new Gson()
                    .fromJson(data, InMemoryToDoRepository.class);
        } else {
            internalRepository = new InMemoryToDoRepository();
        }
    }

    private static String getDataFrom(File file) {
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\A");    // read everything
            return scanner.next();
        } catch (FileNotFoundException e) {
            // ignore because file must exist
        }
        return "";
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(DEFAULT_FILE, false)
        )) {
            writer.write(new Gson().toJson(internalRepository));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ToDoItem> findAll() {
        return internalRepository.findAll();
    }

    @Override
    public ToDoItem findById(Long id) {
        return internalRepository.findById(id);
    }

    @Override
    public Long insert(ToDoItem toDoItem) {
        return internalRepository.insert(toDoItem);
    }

    @Override
    public void update(ToDoItem toDoItem) {
        internalRepository.update(toDoItem);
    }

    @Override
    public void delete(ToDoItem toDoItem) {
        internalRepository.delete(toDoItem);
    }
}
