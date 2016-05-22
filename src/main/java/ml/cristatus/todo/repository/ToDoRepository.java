package ml.cristatus.todo.repository;

import ml.cristatus.todo.model.ToDoItem;

import java.util.List;

/**
 * The interface that sets down the common functionality that every sort of
 * Item Repository must have. The {@link #save() save()} functionality is a
 * useful and sought after feature, but some Repositories may choose not to
 * implement it.
 *
 * @author Subhomoy Haldar
 * @version 0.3
 */
public interface ToDoRepository {
    /**
     * Returns the List of all {@link ToDoItem}s sorted in the order that
     * they were added.
     *
     * @return A chronological list of ToDoItems.
     */
    List<ToDoItem> findAll();

    /**
     * Returns the ToDoItem that has the required ID, or <code>null</code> if
     * no such ToDoItems exists.
     *
     * @param id The unique ID.
     * @return The ToDoItem that has the required ID.
     */
    ToDoItem findById(Long id);

    /**
     * Inserts a new ToDoItem into the repository that has the content given
     * in the argument and returns the ID assigned to it.
     *
     * @param text The content for the new ToDoItem.
     * @return The ID of the Item inserted.
     */
    Long insert(String text);

    /**
     * Updates the ToDoItem currently in the repository with the given key to
     * contain the new text. If no such Item exists with the same key,
     * nothing is done.
     *
     * @param toDoItem The new, updated item to be registered.
     */
    void update(ToDoItem toDoItem);

    /**
     * Deletes the given Item from the repository, if it exists.
     *
     * @param toDoItem The Item to delete.
     */
    void delete(ToDoItem toDoItem);

    /**
     * Saves the current state of the repository to any form of permanent
     * storage. But this behaviour is not compulsory. An implementation may
     * or may not have any saving mechanism.
     */
    void save();
}
