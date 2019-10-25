package com.immomo.test.hacker;

/**
 * 通过经纬度 计算用户之间的距离
 *
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.test.hacker
 * @date 2019-10-25 11:27
 */
public class Location {

    // 平均半径,单位：m；不是赤道半径。赤道为6378左右
    private static final double EARTH_RADIUS = 6371393;

    /**
     * @描述 反余弦进行计算经纬度之间的距离
     **/
    public static double getDistance(Double lat1, Double lng1, Double lat2, Double lng2) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(lng1); // A经弧度
        double radiansAY = Math.toRadians(lat1); // A纬弧度
        double radiansBX = Math.toRadians(lng2); // B经弧度
        double radiansBY = Math.toRadians(lat2); // B纬弧度

        // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
//        System.out.println("cos = " + cos); // 值域[-1,1]
        double acos = Math.acos(cos); // 反余弦值
//        System.out.println("acos = " + acos); // 值域[0,π]
//        System.out.println("∠AOB = " + Math.toDegrees(acos)); // 球心角 值域[0,180]
        return EARTH_RADIUS * acos; // 最终结果
    }


    // 地球赤道半径
    private static double EARTH_RADIUS2 = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * @描述 经纬度获取距离，单位为米
     **/
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS2;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }


    public static void main(String[] args) {
        //121.717594,31.12055    121.817629,31.090867
        double distance = getDistance(31.12055, 121.717594,
                31.090867, 121.817629);
        System.out.println("距离" + distance + "米");
    }


}
