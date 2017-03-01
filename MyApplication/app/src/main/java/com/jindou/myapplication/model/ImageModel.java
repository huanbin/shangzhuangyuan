package com.jindou.myapplication.model;

/**
 * Created by zhishi on 2017/2/24.
 */

public class ImageModel {
    private String ref;
    private String pixel;
    private String alt;
    private String src;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getPixel() {
        return pixel;
    }

    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public ImageModel() {

    }

    @Override
    public String toString() {
        return "ImageModel{" +
                "ref='" + ref + '\'' +
                ", pixel='" + pixel + '\'' +
                ", alt='" + alt + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}
