package de.agentlab.sourcehog.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils {
    public static String[] join(String s, String[] l) {
        List<String> tmp = new ArrayList<>();
        tmp.add(s);
        tmp.addAll(Arrays.asList(l));

        String[] result = new String[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            result[i] = tmp.get(i);
        }
        return result;
    }
}
