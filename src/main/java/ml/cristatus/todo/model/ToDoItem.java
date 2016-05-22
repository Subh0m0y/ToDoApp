package ml.cristatus.todo.model;

/**
 * This is the basic unit of information in this system. Everything is built
 * around this class. This class defines a basic task that has a unique ID
 * that is assigned by a repository. The "name" or content of a task can be
 * updated freely. But the ID is updated only once by the repository. Every
 * task also has a <code>completed</code> boolean flag to indicate its state
 * of completion.
 *
 * @author Subhomoy Haldar
 * @version 0.3
 */
public class ToDoItem implements Comparable<ToDoItem> {

    /**
     * The unique and immutable ID.
     */
    private final long id;
    /**
     * The modifiable content of the item.
     */
    private String name;
    /**
     * The status of the task/item.
     */
    private boolean completed;

    /**
     * This constructor creates a new ToDoItem with ta unique ID and text.
     *
     * @param name The name of the task, or its content.
     * @param id   The unique ID of the item.
     */
    public ToDoItem(String name, final long id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Compares two tasks based on their order of creation. A ToDoItem
     * created later will have a higher ID.
     *
     * @param item The ToDoItem to compare this item with.
     * @return -1, 0, or 1 if this item is created earlier, at the same time as
     * the argument, or created after the argument, respectively.
     */
    @Override
    public int compareTo(ToDoItem item) {
        return Long.compare(id, item.id);
    }

    /**
     * Returns the ID of the ToDoItem.
     *
     * @return The ID of the ToDoItem.
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the name of the task.
     *
     * @return The name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the contents of this task.
     *
     * @param name The new name of the task.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the current status of this item. If the task is completed, it
     * returns <code>true</code> and <code>false</code> otherwise.
     *
     * @return The current status of this item.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Set the completion status of this task.
     *
     * @param completed The new status to be set.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Check if the argument is exactly equal to this item.
     *
     * @param other The argument to compare with.
     * @return <code>true</code> if the objects are equal, <code>false</code>
     * otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ToDoItem)) return false;
        ToDoItem item = (ToDoItem) other;
        return id == item.id && name.equals(item.name);
    }

    /**
     * Returns a handy String representation of the item.
     *
     * @return A handy String representation of the item.
     */
    @Override
    public String toString() {
        return " - " + name + " [ID: " + id + " Completed: " + completed + "]";
    }
}
