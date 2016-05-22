package ml.cristatus.todo.repository;

import ml.cristatus.todo.model.ToDoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This is an implementation of ToDoRepository that stores the items in
 * memory. This does not have a save mechanism. It must by backed by another
 * system to provide support for persistence.
 *
 * @author Subhomoy Haldar
 * @version 0.3
 */
@SuppressWarnings("WeakerAccess")
public class InMemoryToDoRepository implements ToDoRepository {

    private AtomicLong currentId = new AtomicLong();
    private ConcurrentMap<Long, ToDoItem> toDos
            = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public List<ToDoItem> findAll() {
        List<ToDoItem> toDoItems = new ArrayList<>(toDos.values());
        Collections.sort(toDoItems);
        return toDoItems;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public ToDoItem findById(Long id) {
        return toDos.get(id);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Long insert(String text) {
        Long id = currentId.incrementAndGet();
        toDos.putIfAbsent(id, new ToDoItem(text, id));
        return id;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public void update(ToDoItem toDoItem) {
        if (toDos.get(toDoItem.getId()) != null) {
            toDos.replace(toDoItem.getId(), toDoItem);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public void delete(ToDoItem toDoItem) {
        toDos.remove(toDoItem.getId());
        if (toDos.isEmpty()) {
            reset();
        }
    }

    /**
     * This class does NOT provide a saving mechanism.
     */
    @Override
    public void save() {
        // no save mechanism here
    }

    /**
     * Resets the currentID to 1 when the list is empty.
     */
    private void reset() {
        currentId = new AtomicLong();
    }
}
