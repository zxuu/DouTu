package com.zxu.picturesxiangce.util;

import java.util.HashMap;
import java.util.List;

public class MyHashMap<k> extends HashMap<k, String> {

    @Override
    public String put(k key, String value) {
        String newV = value;
        if (containsKey(key)) {
            String oldV = get(key);
            newV = oldV + ";" + newV;
        }
        return super.put(key, newV);
    }
}
