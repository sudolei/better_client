package com.example.better_client.util;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyUtil {


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
}
