package com.grupoprominente.viatify.model;

import java.io.Serializable;

public class SubOrganization implements Serializable {
    private int id;
    private String title;
    private Organization org;

    public SubOrganization() {
    }

    public SubOrganization(int id, String title, Organization org){
        this.id = id;
        this.title  = title;
        this.org = org;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Organization getOrg() { return org;  }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOrg(Organization org) { this.org = org; }

}
