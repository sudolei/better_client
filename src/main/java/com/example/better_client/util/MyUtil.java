package com.example.better_client.util;

import javafx.collections.ObservableList;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtil {


    public static String[] resetArr(String[] args) {
        List<String> list = new ArrayList<>();
        for (String str : args) {
            int num = getPdfCount(str);
            for (int i = 0; i < num; i++) {
                list.add(str);
            }
        }
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }


    public static String[] resetByHcArr(String[] args) {
        List<String> list = new ArrayList<>();
        for (String str : args) {
            list.add(str);
        }
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }


    public static int getPdfCount(String fileName) {
        fileName = fileName.toUpperCase(Locale.ROOT);
        String regex = "\\d+FEN";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileName);
        String count = null;
        if (matcher.find()) {
            count = matcher.group();
        }
        int result = 1;
        if (count != null) {
            result = getStrMath(count);
        }
        return result;
    }

    public static int getStrMath(String input) {
        Pattern pattern = Pattern.compile("\\d+"); // 匹配一个或多个数字
        Matcher matcher = pattern.matcher(input);
        int result = 0;
        while (matcher.find()) {
            // 打印匹配到的数字
//            System.out.println(matcher.group());
            result = Integer.parseInt(matcher.group());
        }
        return result;
    }

    public static String volidate(ObservableList<String> observableList) {
        Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for (String str : observableList) {
            String key = PdfUtil.getWhMapData(str);
            if (map.containsKey(key)) {
                ArrayList<String> l = map.get(key);
                if (l == null || l.size() <= 0) {
                    l = new ArrayList<>();
                }
                l.add(str);
                map.put(key, l);
            } else {
                ArrayList<String> l = new ArrayList<String>();
                l.add(str);
                map.put(key, l);
            }
        }
        String w = "";
        if (map.size() != 1) {
            String minKey = null;
            int minSize = Integer.MAX_VALUE;

            for (String key : map.keySet()) {
                List<String> list = map.get(key);
                int size = list.size();
                if (size < minSize) {
                    minSize = size;
                    minKey = key;
                }
            }
            List<String> list = map.get(minKey);

            for (String str : list) {
                w += str + "\n";
            }
        }
        return w;
    }


    public static String getResultFileName(ObservableList<String> observableList) {
        String result = "";
        Set<String> s = new HashSet<>();
        for (String str : observableList) {
            String fileName = str.substring(str.lastIndexOf(File.separator) + 1);
            String[] pathArray = fileName.split("--");
            String name = pathArray[0] + "--" + pathArray[1];
            s.add(name);
        }

        for (String str : s) {
            result += str;
        }
        result += ".pdf";
        return result;
    }
}
