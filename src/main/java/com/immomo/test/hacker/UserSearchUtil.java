package com.immomo.test.hacker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserSearchUtil {

    private static final double LAT_30KM = 0.27;
    private static final double LNG_30KM = 0.45;
    private static final int DIS_30KM = 30_000;
    // 地球赤道半径
    private static double EARTH_RADIUS2 = 6378.137;

    /**
     * @param list
     * @param size
     * @return
     */
    public static List<User> findMinDistanceUsers(List<List<User>> list, int size) {
        User[] userList = new User[size];
        double maxDis = DIS_30KM;
        int curSize = 0;

        for (int i = 0; i < list.size(); i++) {

            List<User> curList = list.get(i);

            if (curList.isEmpty()) {
                continue;
            }

            for (int j = 0; j < curList.size(); j++) {

                if (curSize >= size && curList.get(j).getDistance() > maxDis) {
                    break;
                }

                pull(userList, curSize, curList.get(j));

                if (curSize < size) {
                    curSize++;
                }

                maxDis = userList[curSize - 1].getDistance();
            }
        }

        List<User> resultList = new ArrayList<>();
        for (int i = 0; i < curSize; i++) {
            resultList.add(userList[i]);
        }

        return resultList;
    }

    /**
     * @param provinceUserInfo 省份用户信息
     * @param curLat           指定位置的维度
     * @param curLng           指定位置的经度
     * @param size             返回最近的数据条数
     * @return
     */
    public static List<User> getNearest30kmUsers(Province provinceUserInfo, double curLat, double curLng, int size) {
        if (curLat > provinceUserInfo.getMaxLat() + LAT_30KM
                || curLat < provinceUserInfo.getMinLat() - LAT_30KM
                || curLng > provinceUserInfo.getMaxLng() + LNG_30KM
                || curLng < provinceUserInfo.getMinLng() - LNG_30KM) {
            return Arrays.asList();
        }

        if (provinceUserInfo.getFemales() == null) {
            return Arrays.asList();
        }

        double maxLat = curLat + LAT_30KM;
        double minLat = curLat - LAT_30KM;
        double maxLng = curLng + LNG_30KM;
        double minLng = curLng - LNG_30KM;

        User[] userList = new User[size];
        double maxDis = DIS_30KM;
        int curSize = 0;
        for (User user : provinceUserInfo.getFemales()) {
            if (user.getLat() < minLat || user.getLat() > maxLat) {
                continue;
            }

            if (user.getLng() < minLng || user.getLng() > maxLng) {
                continue;
            }

            double distance = getDistance(user.getLat(), user.getLng(), curLat, curLng);
            if (distance > DIS_30KM) {
                continue;
            }

            if (curSize >= size && distance > maxDis) {
                continue;
            }

            User appendUser = new User();
            appendUser.setMomoId(user.getMomoId());
            appendUser.setDistance(distance);
            pull(userList, curSize, appendUser);
            if (curSize < size) {
                curSize++;
            }

            maxDis = userList[curSize - 1].getDistance();
        }

        List<User> resultList = new ArrayList<User>();
        for (int i = 0; i < curSize; i++) {
            resultList.add(userList[i]);
        }

        return resultList;
    }

    private static void pull(User[] userList, int curSize, User appendUser) {
        if (curSize >= userList.length) {
            userList[userList.length - 1] = appendUser;
        } else {
            userList[curSize] = appendUser;
            curSize++;
        }

        for (int i = curSize - 1; i > 0; i--) {
            if (userList[i].getDistance() > userList[i - 1].getDistance()) {
                break;
            }

            User tmp = userList[i];
            userList[i] = userList[i - 1];
            userList[i - 1] = tmp;
        }
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

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}
