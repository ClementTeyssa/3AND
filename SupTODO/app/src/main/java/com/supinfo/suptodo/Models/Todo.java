package com.supinfo.suptodo.Models;


public class Todo {

    private int id;
    private String label;
    private TodoList belongingList;

    public Todo(int id, String label, TodoList belongingList) {
        this.id = id;
        this.label = label;
        this.belongingList = belongingList;
    }

    public Todo(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TodoList getBelongingList() {
        return belongingList;
    }

    public void setBelongingList(TodoList belongingList) {
        this.belongingList = belongingList;
    }
}
