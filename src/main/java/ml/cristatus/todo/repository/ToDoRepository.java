package ml.cristatus.todo.repository;

import ml.cristatus.todo.model.ToDoItem;

import java.util.List;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
public interface ToDoRepository {
    List<ToDoItem> findAll();
    ToDoItem findById(Long id);
    Long insert(ToDoItem toDoItem);
    void update(ToDoItem toDoItem);
    void delete(ToDoItem toDoItem);
}
