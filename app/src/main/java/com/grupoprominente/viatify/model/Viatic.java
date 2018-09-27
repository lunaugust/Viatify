package com.grupoprominente.viatify.model;

public class Viatic {
    public static final String TABLE_NAME = "viatics";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_IMGPATH = "imgpath";

    private int id;
    private String title;
    private String description;
    private Double amount;
    private String timestamp;
    private String imgpath;
    private int color = -1;



    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_TITLE + " TEXT, "
                    + COLUMN_DESCRIPTION + " TEXT, "
                    + COLUMN_AMOUNT + " REAL, "
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
                    + COLUMN_IMGPATH + " TEXT"
                    + ")";

    public Viatic() {
    }

    public Viatic(int id, String title, String description, Double amount, String timestamp, String imgpath){
        this.id = id;
        this.title  = title;
        this.description = description;
        this.amount = amount;
        this.timestamp = timestamp;
        this.imgpath = imgpath;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() { return amount; }

    public String getTimestamp() {
        return timestamp;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(Double amount) { this.amount = amount; }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
