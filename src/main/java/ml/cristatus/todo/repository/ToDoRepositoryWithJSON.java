package ml.cristatus.todo.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ml.cristatus.todo.model.ToDoItem;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * This implementation provides a save mechanism. It looks for a
 * <code>toDoData.json</code> file in the <code>Documents</code> folder (or
 * creates one if it can't find one). Then it saves the data in JSON format.
 * <p>
 * It uses an instance of {@link InMemoryToDoRepository} internally inorder
 * to reuse existing functionality.
 *
 * @author Subhomoy Haldar
 * @version 0.3
 */
public class ToDoRepositoryWithJSON implements ToDoRepository {

    /**
     * This is the default save location, which is expected to be same
     * throughout all platforms (hopefully).
     */
    private static final File DEFAULT_FILE = new File(
            System.getProperty("user.home") + "/Documents/toDoData.json"
    );
    /**
     * The Gson instance used every time to provide conversion functionality
     * between JSON and Java representation.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    /**
     * If a terminal session extends for too long and some problem occurs at
     * the end, then a lot of data might be lost. Therefore, this class
     * auto saves after a regular period (number of operations).
     */
    private static final int AUTO_SAVE_COUNT = 15;

    /**
     * This class basically wraps around this repository.
     */
    private InMemoryToDoRepository internalRepository;
    /**
     * Operation count. Necessary for autoSave().
     */
    private int operationCount;

    /**
     * Creates a new repository by loading saved data, if available.
     */
    public ToDoRepositoryWithJSON() {
        if (DEFAULT_FILE.exists()) {
            String data = getDataFrom(DEFAULT_FILE);
            internalRepository =
                    GSON.fromJson(data, InMemoryToDoRepository.class);
        } else {
            internalRepository = new InMemoryToDoRepository();
        }
        operationCount = 0;
    }

    /**
     * Reads in all the data from the given file (which MUST exist) and
     * returns it in the form of a String.
     *
     * @param file The File to read.
     * @return A String containing all the data in the File.
     */
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

    /**
     * Writes the contents of the internalRepository to the default file.
     */
    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(DEFAULT_FILE, false) // overwrite the file
        )) {
            writer.write(GSON.toJson(internalRepository));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public List<ToDoItem> findAll() {
        autoSave();
        return internalRepository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public ToDoItem findById(Long id) {
        autoSave();
        return internalRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Long insert(String text) {
        autoSave();
        return internalRepository.insert(text);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public void update(ToDoItem toDoItem) {
        autoSave();
        internalRepository.update(toDoItem);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public void delete(ToDoItem toDoItem) {
        autoSave();
        internalRepository.delete(toDoItem);
    }

    /**
     * Automatically saves progress after a certain number of operations.
     */
    private void autoSave() {
        operationCount++;
        if (operationCount % AUTO_SAVE_COUNT == 0) {
            save();
        }
    }
}
