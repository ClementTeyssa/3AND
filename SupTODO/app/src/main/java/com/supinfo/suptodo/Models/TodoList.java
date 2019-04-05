package com.supinfo.suptodo.Models;

import java.io.Serializable;

public class TodoList implements Serializable {

    private  int id;
    private String name;
    private User owner;
    private User sharedWith;
    private Todo[] todos;

    public TodoList(int id, String name, User owner, User sharedWith) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.sharedWith = sharedWith;
    }

    public TodoList(int id, String name, User owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public TodoList(User user, String name) {
        this.name = name;
        this.owner = user;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(User sharedWith) {
        this.sharedWith = sharedWith;
    }

    public Todo[] getTodos() {
        return todos;
    }

    public void setTodos(Todo[] todos) {
        this.todos = todos;
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", sharedWith=" + sharedWith +
                '}';
    }
}
