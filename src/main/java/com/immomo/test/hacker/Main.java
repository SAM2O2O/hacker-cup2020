package com.immomo.test.hacker;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.test.hacker
 * @date 2019-10-25 10:39
 */
public class Main {

    private static Pattern pattern = Pattern.compile("\\{\"momoId\":([0-9]+),\"lat\":([0-9\\.]+),\"lng\":([0-9\\.]+),\"sex\":\"([\\w]+)\"");

    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    private static volatile User master = null;

    private static volatile AtomicInteger TOTAL_COUNTER = new AtomicInteger();

    private static List<List<User>> topNearestUserList = new ArrayList<>();


    /**
     * args[0] 文件夹路径
     * args[1] 男用户memoid
     *
     * @param args
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        /**
         * 文件目录
         */
        final String fileDir = "/Users/momo/IdeaProjects/momo-im/hackercup/src/main/resources/location";

        // 用户的id
        final String momoid = "14459344";


        File parentFile = new File(fileDir);


        File[] allFiles = parentFile.listFiles();


        int fileSize = allFiles.length;
        TOTAL_COUNTER.set(fileSize);


        for (int i = 0; i < fileSize; i++) {

            final File f = allFiles[i];

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Province province = findUserFromProvince(f, momoid);
                    System.out.println("file=" + f.getName() + " province=" + province);

                    //
                    if (inProvince()) {
                        List<User> nearestUsers = UserSearchUtil.getNearest30kmUsers(province, master.getLat(), master.getLng(), 10);
                        topNearestUserList.add(nearestUsers);
                    }

                    int count = TOTAL_COUNTER.decrementAndGet();

                    // 每个省份的做完，去合并一次
                    if (count <= 0) {

                        System.out.println("----end");
                        List<User> topUser = UserSearchUtil.findMinDistanceUsers(topNearestUserList, 10);

                        System.out.println("======top 10 user=" + topUser);

                        executorService.shutdown();
                        System.exit(0);
                    }

                }
            });

        }


        System.out.println("program finish ,cost=" + (System.currentTimeMillis() - startTime));
    }

    private static boolean inProvince() {
        return true;
    }

    private static Province findUserFromProvince(File file, String momoid) {

        Province province = new Province();

        InputStreamReader inputReader = null;
        BufferedReader bf = null;
        try {

            inputReader = new InputStreamReader(new FileInputStream(file));
            bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                User user = match(str);

                if (province.getMinLng() == 0 || province.getMinLat() == 0) {
                    province.setMinLat(user.getLat());
                    province.setMinLng(user.getLng());
                }

                if (user.getSex().startsWith("F") || user.getSex().startsWith("f")) {
                    province.addFemale(user);

                    if (user.getLat() > province.getMaxLat()) {
                        province.setMaxLat(user.getLat());
                    }

                    if (user.getLat() < province.getMinLat()) {
                        province.setMinLat(user.getLat());
                    }

                    if (user.getLng() > province.getMaxLng()) {
                        province.setMaxLng(user.getLng());
                    }

                    if (user.getLng() < province.getMinLng()) {
                        province.setMinLng(user.getLng());
                    }
                } else {
                    // 男性
                    if (master == null && user != null && momoid.equals(user.getMomoId())) {
                        master = user;
                        System.out.println("find you master=" + master);
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputReader != null) {
                try {
                    inputReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 这里计算。。。
        return province;
    }


    private static User match(String jsonStr) {
        final Matcher matcher = pattern.matcher(jsonStr);

        while (matcher.find()) {
            return new User().setMomoId(matcher.group(1))
                    .setLat(Double.parseDouble(matcher.group(2)))
                    .setLng(Double.parseDouble(matcher.group(3)))
                    .setSex(matcher.group(4));
        }

        return null;
    }


    private static List<User> getNearest30kmUsers(Province province, User master) {

        return Arrays.asList(null);
    }


}
