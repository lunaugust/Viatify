package com.grupoprominente.viatify.model;

public class ServiceLines {
    public static final String TABLE_NAME = "service_lines";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SUB_ORG_ID = "sub_org_id";

    private int id;
    private String title;
    private int sub_org_id;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY, "
                    + COLUMN_TITLE + " TEXT, "
                    + COLUMN_SUB_ORG_ID + " INTEGER"
                    + ")";

    public ServiceLines() {
    }

    public ServiceLines(int id, String title, int sub_org_id){
        this.id = id;
        this.title  = title;
        this.sub_org_id = sub_org_id;
    }

    public Integer getId() {
        return Integer.valueOf(id);
    }

    public String getTitle() {
        return title;
    }

    public Integer getSub_org_id() { return Integer.valueOf(sub_org_id);  }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSub_org_id(int sub_org_id) { this.sub_org_id = sub_org_id; }

}
