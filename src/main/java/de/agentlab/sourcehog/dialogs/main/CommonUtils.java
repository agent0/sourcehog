package de.agentlab.sourcehog.dialogs.main;

public class CommonUtils {
    public static boolean containsIgnoreCase(String s, String p) {
        if (s != null && p != null) {
            return s.toLowerCase().contains(p.toLowerCase());
        }
        return false;
    }
}
