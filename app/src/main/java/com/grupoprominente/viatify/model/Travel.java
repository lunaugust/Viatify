package com.grupoprominente.viatify.model;

public class Travel {
    public static final String TABLE_NAME = "travel";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";

    private int id;
    private String title;
    private int color = -1;



    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_TITLE + " TEXT"
                    + ")";

    public Travel() {
    }

    public Travel(int id, String title ){
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
