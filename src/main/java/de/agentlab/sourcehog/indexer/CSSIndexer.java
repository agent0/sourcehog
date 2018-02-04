package de.agentlab.sourcehog.indexer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CSSIndexer extends AbstractIndexer {

    private static String[] keywords = new String[]{};
    private static Map<String, String> keywordMap = new HashMap<>();

    static {
        Arrays.stream(keywords).forEach(k -> keywordMap.put(k, k));
    }

    public String[] getExcludeDirs() {
        return exclude_dirs;
    }

    public String[] getExcludeExt() {
        return new String[]{};
    }

    public String[] getIncludeExt() {
        return new String[]{"css"};
    }

    @Override
    protected boolean skip(String line) {
        return !line.contains("{");
    }

    @Override
    protected String getSanitationRegex() {
        return "[^A-Za-z0-9_]";
    }

    @Override
    protected Map<String, String> getKeywordMap() {
        return keywordMap;
    }
}
