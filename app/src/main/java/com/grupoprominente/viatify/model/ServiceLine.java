package com.grupoprominente.viatify.model;

import java.io.Serializable;

public class ServiceLine implements Serializable {
    private int id;
    private String title;
    private SubOrganization sub_org;

    public ServiceLine() {
    }

    public ServiceLine(int id, String title, SubOrganization sub_org){
        this.id = id;
        this.title  = title;
        this.sub_org = sub_org;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public SubOrganization getSub_org() { return sub_org;  }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSub_org(SubOrganization sub_org) { this.sub_org = sub_org; }

    @Override
    public String toString() {
        return title + "-" + sub_org.getTitle() + "-" + sub_org.getOrg().getTitle();
    }
}
