package com.grupoprominente.viatify.model;

public class ServiceLines {
    private int id;
    private String title;
    private int sub_org_id;

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
