package com.example.fogfly.smarter.entity;

import java.io.Serializable;

/**
 * @author Zaki Xue
 * @time 2019/2/28 1:07
 * @description
 */
public class DiscoverSteps implements Serializable {
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    private String img;
    private String step;
}
