package de.agentlab.sourcehog.utils;

public class CommonUtils {
    public static boolean containsIgnoreCase(String s, String p) {
        if (s != null && p != null) {
            return s.toLowerCase().contains(p.toLowerCase());
        }
        return false;
    }
}
