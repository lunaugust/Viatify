package com.grupoprominente.viatify.model;

public class Organizations {
    private int id;
    private String title;

    public Organizations() {
    }

    public Organizations(int id, String title){
        this.id = id;
        this.title  = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
