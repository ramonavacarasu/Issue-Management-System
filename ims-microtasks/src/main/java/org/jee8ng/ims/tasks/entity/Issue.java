package org.jee8ng.ims.tasks.entity;

public class Issue {

    private Long id;
    private String name;
    private String priority;

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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Issue{" + "id=" + id + ", name=" + name + ", priority=" + priority + '}';
    }
}
