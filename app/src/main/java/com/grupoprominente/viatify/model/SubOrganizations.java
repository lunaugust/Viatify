package com.grupoprominente.viatify.model;

public class SubOrganizations {
    public static final String TABLE_NAME = "sub_organizations";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ORG_ID = "org_id";

    private int id;
    private String title;
    private int org_id;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY, "
                    + COLUMN_TITLE + " TEXT, "
                    + COLUMN_ORG_ID + " INTEGER"
                    + ")";

    public SubOrganizations() {
    }

    public SubOrganizations(int id, String title, int org_id){
        this.id = id;
        this.title  = title;
        this.org_id = org_id;
    }

    public Integer getId() {
        return Integer.valueOf(id);
    }

    public String getTitle() {
        return title;
    }

    public Integer getOrg_id() { return Integer.valueOf(org_id);  }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOrg_id(int org_id) { this.org_id = org_id; }

}
