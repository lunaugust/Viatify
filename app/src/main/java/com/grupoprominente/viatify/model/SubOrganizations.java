package com.grupoprominente.viatify.model;

import java.util.ArrayList;
import java.util.List;

public class SubOrganizations {
    private int id;
    private String title;
    private int org_id;


    public SubOrganizations() {
    }

    public SubOrganizations(int id, String title, int org_id){
        this.id = id;
        this.title  = title;
        this.org_id = org_id;
    }

    public int getId() { return id;}

    public String getTitle() {
        return title;
    }

    public int getOrg_id() { return org_id;  }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOrg_id(int org_id) { this.org_id = org_id; }


}
