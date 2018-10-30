package com.grupoprominente.viatify.model;

public class Organizations {
    public static final String TABLE_NAME = "organizations";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";

    private int id;
    private String title;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY, "
                    + COLUMN_TITLE + " TEXT "
                    + ")";

    public Organizations() {
    }

    public Organizations(int id, String title){
        this.id = id;
        this.title  = title;
    }

    public Integer getId() {
        return Integer.valueOf(id);
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
