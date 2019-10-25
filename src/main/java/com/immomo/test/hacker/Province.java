package com.immomo.test.hacker;

import java.util.LinkedList;
import java.util.List;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.test.hacker
 * @date 2019-10-25 14:34
 */
public class Province {

    private String name;
    private double maxLat;
    private double minLat;
    private double maxLng;
    private double minLng;

    private List<User> females = new LinkedList<User>();


    private String lastMomoid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    public double getMinLat() {
        return minLat;
    }

    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    public double getMaxLng() {
        return maxLng;
    }

    public void setMaxLng(double maxLng) {
        this.maxLng = maxLng;
    }

    public double getMinLng() {
        return minLng;
    }

    public void setMinLng(double minLng) {
        this.minLng = minLng;
    }

    public List<User> getFemales() {
        return females;
    }

    public void setFemales(List<User> females) {
        this.females = females;
    }

    public void addFemale(User female) {
        this.females.add(female);
    }

    public String getLastMomoid() {
        return lastMomoid;
    }

    public void setLastMomoid(String lastMomoid) {
        this.lastMomoid = lastMomoid;
    }

    @Override
    public String toString() {
        return "Province{" +
                "maxLat=" + maxLat +
                ", minLat=" + minLat +
                ", maxLng=" + maxLng +
                ", minLng=" + minLng +
                ", females=" + females +
                ", lastMomoid='" + lastMomoid + '\'' +
                '}';
    }
}
