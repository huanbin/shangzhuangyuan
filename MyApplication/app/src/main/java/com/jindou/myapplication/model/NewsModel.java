package com.jindou.myapplication.model;

public class NewsModel {
    private String title, type,source;
    private int pic;

    public NewsModel() {
    }

    public NewsModel(String title, String source, int pic, String type) {
        this.title = title;
        this.source = source;
        this.pic = pic;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
