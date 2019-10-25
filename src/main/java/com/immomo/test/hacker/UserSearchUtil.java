package com.immomo.test.hacker;

import java.util.ArrayList;
import java.util.List;

public class UserSearchUtil {

    private static final double LAT_30KM = 0.27;
    private static final double LNG_30KM = 0.45;
    private static final int DIS_30KM = 30_000;

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
        if (provinceUserInfo.getFemales() == null) {
            return null;
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

            double distance = Location.getDistance(user.getLat(), user.getLng(), curLat, curLng);
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

    public static void main(String[] args) {
        System.out.println(Location.getDistance(53.0, 135.5, 53.0, 135.82));
    }
}
