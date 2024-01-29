package org.pytorch.demo.objectdetection;

public class DataModel {
    String title;
    int image_path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage_path() {
        return image_path;
    }

    public void setImage_path(int image_path) {
        this.image_path = image_path;
    }

    public DataModel(String title, int image_path) {
        this.title = title;
        this.image_path = image_path;
    }
}