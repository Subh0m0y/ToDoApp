package ml.cristatus.todo.model;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
public class ToDoItem implements Comparable<ToDoItem> {

    private Long id;
    private String name;
    private boolean completed;

    public ToDoItem(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(ToDoItem item) {
        return Long.compare(id, item.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ToDoItem)) return false;
        ToDoItem item = (ToDoItem) other;
        return id.equals(item.id) && name.equals(item.name);
    }

    @Override
    public String toString() {
        return " - " + name + " [ID: " + id + " Completed: " + completed + "]";
    }
}
