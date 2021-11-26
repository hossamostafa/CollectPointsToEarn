package com.koshy.collectpoints.models;

public class ContainerModel {
    String fireId;
    String img;
    String name;
    String link;

    public ContainerModel() {
    }

    public ContainerModel(String img, String name, String link) {
        this.img = img;
        this.name = name;
        this.link = link;
    }

    public String getFireId() {
        return fireId;
    }

    public void setFireId(String fireId) {
        this.fireId = fireId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
