package org.pytorch.demo.objectdetection;

import android.widget.Button;

public class DataMovie {
    int image;
    String title;
    Button button;

    public DataMovie(int image, String title){
        this.image = image;
        this.title = title;
        this.button = button;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Button getButton() { return button; }

    public void setButton(Button button) { this.button = button; }
}