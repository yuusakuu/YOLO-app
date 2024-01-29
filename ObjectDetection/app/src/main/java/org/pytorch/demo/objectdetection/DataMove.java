package org.pytorch.demo.objectdetection;

public class DataMove {
    int image;

    public DataMove(int image){
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}