package com.example.rgukt.gruppe;

public class Image {

    private String mtimestamp;
    private String mliked;
    private String murl;

    public Image(String mtimestamp, String mliked, String murl) {
        this.mtimestamp = mtimestamp;
        this.mliked = mliked;
        this.murl = murl;
    }

    public String getMurl() {
        return murl;
    }

    public String getMtimestamp() {
        return mtimestamp;
    }

    public String getMliked() {
        return mliked;
    }
}
