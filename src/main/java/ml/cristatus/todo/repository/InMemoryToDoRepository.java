package ml.cristatus.todo.repository;

import ml.cristatus.todo.model.ToDoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Subhomoy Haldar
 * @version 0.3
 */
@SuppressWarnings("WeakerAccess")
public class InMemoryToDoRepository implements ToDoRepository {

    private AtomicLong currentId = new AtomicLong();
    private ConcurrentMap<Long, ToDoItem> toDos
            = new ConcurrentHashMap<>();

    @Override
    public List<ToDoItem> findAll() {
        List<ToDoItem> toDoItems = new ArrayList<>(toDos.values());
        Collections.sort(toDoItems);
        return toDoItems;
    }

    @Override
    public ToDoItem findById(Long id) {
        return toDos.get(id);
    }

    @Override
    public Long insert(String text) {
        Long id = currentId.incrementAndGet();
        toDos.putIfAbsent(id, new ToDoItem(text, id));
        return id;
    }

    @Override
    public void update(ToDoItem toDoItem) {
        if (toDos.get(toDoItem.getId()) != null) {
            toDos.replace(toDoItem.getId(), toDoItem);
        }
    }

    @Override
    public void delete(ToDoItem toDoItem) {
        toDos.remove(toDoItem.getId());
        if (toDos.isEmpty()) {
            reset();
        }
    }

    @Override
    public void save() {
        // no save mechanism here
    }

    private void reset() {
        currentId = new AtomicLong();
    }
}
