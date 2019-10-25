package com.immomo.test.hacker;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.test.hacker
 * @date 2019-10-25 14:36
 */
public class User {

    private String momoId;
    private double lat;
    private double lng;
    private String sex;

    private double distance;

    public String getMomoId() {
        return momoId;
    }

    public User setMomoId(String momoId) {
        this.momoId = momoId;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public User setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public User setLng(double lng) {
        this.lng = lng;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public User setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "User{" +
                "momoId='" + momoId + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", sex='" + sex + '\'' +
                '}';
    }
}
